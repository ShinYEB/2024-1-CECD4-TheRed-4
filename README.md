<img src = "image/title.png">
<br>
<br>

# 🍎 Stock Signal 프로젝트 소개

### “초보 투자자들이 쉽고 안전하게, 주식 투자의 경험을 쌓도록 돕습니다.”
주식 투자에 어려움을 겪는 주린이(주식+어린이)를 위해, 기존 MTS의 복잡한 요소를 개선한 간편한 주식 관리 시스템을 제공합니다. 

## 🔧 기술 스택 & 아키텍처

### 핵심 기술
- **언어**: Swift 5
- **아키텍처**: Clean Architecture + MVVM
- **반응형 프로그래밍**: RxSwift, RxCocoa, RxAlamofire
- **네트워크**: Alamofire, URLSession
- **머신러닝**: Core ML, GRU(Gated Recurrent Unit) 모델
- **UI 프레임워크**: UIKit, SnapKit(선언적 Auto Layout)
- **UI 패턴**: Compositional Layout, DiffableDataSource
- **동시성 처리**: DispatchQueue, ConcurrentDispatchQueueScheduler
- **메모리 관리**: ARC, 적절한 [weak self] 참조 사용

### 엔지니어링 특징
- **계층형 아키텍처**: 관심사 분리 원칙에 따른 코드 구성
- **의존성 주입**: 인터페이스를 통한 느슨한 결합
- **메모리 관리**: 순환 참조 방지 및 리소스 해제 최적화

## 💻 프로젝트 구조 (Clean Architecture)

```
├── Application     # 앱의 진입점 및 설정
│   ├── AppDelegate
│   ├── Assets
│   ├── Config.xcconfig    # 환경 설정 관리
│   └── SceneDelegate
├── Data            # 데이터 액세스 계층
│   ├── Network     # API 통신 관련 코드
│   │   ├── Network.swift           # 네트워크 핵심 로직
│   │   ├── NetworkProvider.swift   # 의존성 주입 컨테이너
│   │   ├── StockNetwork.swift      # 주식 API 통신
│   │   └── ...
│   └── Repositories      # 데이터 저장소 구현
│       └── GRUProvider.swift   # 머신러닝 모델 관리
├── Domain          # 비즈니스 로직 계층
│   └── Entities    # 데이터 모델 정의
│       ├── Stock.swift
│       ├── Scenario.swift
│       ├── StockDetail.swift
│       └── ...
└── Presentation    # UI 계층 (MVVM 패턴)
    ├── StockDetail
    │   ├── StockDetailViewController.swift
    │   ├── StockDetailViewModel.swift
    │   └── Cell    # UI 컴포넌트
    ├── NewScenario
    │   ├── NewScenarioViewController.swift
    │   ├── NewScenarioViewModel.swift
    │   └── ...
    └── ...
```

## 🧠 핵심 기술 구현

### 1. 네트워크 레이어 추상화 (Generic과 RxSwift 활용)

```swift
class Network<T: Decodable> {
    private let endpoint: String
    private let queue: ConcurrentDispatchQueueScheduler
    private let token: String
    
    init(_ endpoint: String, token: String) {
        self.endpoint = endpoint
        self.token = token
        self.queue = ConcurrentDispatchQueueScheduler(qos: .background)
    }
    
    func getItemList(path: String, defaultValue: T) -> Observable<T> {
        let fullPath = "\(endpoint)/\(path)"
        return RxAlamofire.data(.get, fullPath, 
                               headers: ["Authorization": "Bearer \(self.token)"])
            .map { data -> T in
                return try JSONDecoder().decode(T.self, from: data)
            }
            .catchError { error in
                print("Error occurred: \(error)")
                return Observable.just(defaultValue) // 안전한 에러 처리
            }
            .observeOn(self.queue)
    }
    
    // POST, PATCH, DELETE 등 다양한 HTTP 메소드 지원
    // ...
}
```

이 추상화된 네트워크 레이어는 다음과 같은 장점을 제공합니다:

- 제네릭을 활용한 타입 안전성 보장
- RxSwift를 통한 비동기 처리 단순화
- 일관된 에러 처리 메커니즘
- 백그라운드 스레드에서의 디코딩 처리로 메인 스레드 부하 감소

### 2. MVVM 아키텍처 (Input/Output 패턴)

```swift
final class StockDetailViewModel {
    // 의존성
    private let stockNetwork: StockNetwork
    private let predictionProvider: GRUProvider
    
    // Input/Output 패턴으로 명확한 데이터 흐름 정의
    struct Input {
        let stockName: String
        let aipredictTrigger: Observable<Void>
    }
    
    struct Output {
        let stockItem: Observable<PeriodPrice>
        let predItem: Observable<PredictData>
    }
    
    public func transform(input: Input) -> Output {
        // 주식 상세 정보 요청
        let stockItem = stockNetwork.getStockDetail(name: input.stockName)
            .share() // 여러 구독자에게 동일한 결과 공유
        
        // AI 예측 트리거 시 GRU 모델 실행
        let predItem = input.aipredictTrigger
            .withLatestFrom(stockItem)
            .map { [weak self] stockData -> PredictData in
                guard let self = self else { return PredictData() }
                
                // 주가 데이터로 AI 예측 수행
                let predictedPrices = self.predictionProvider.predict(items: stockData.periodPrice)
                return PredictData(stockItem: stockData, predItems: predictedPrices)
            }
            .share()
        
        return Output(stockItem: stockItem, predItem: predItem)
    }
}
```

이 구현의 주요 장점:

- Input/Output 패턴으로 명확한 데이터 흐름 제공
- RxSwift 연산자를 활용한 복잡한 비동기 작업 처리
- ViewModel의 단일 책임 원칙 준수
- 메모리 누수 방지를 위한 [weak self] 사용

### 3. Core ML을 활용한 GRU 모델 구현

```swift
class GRUProvider {
    // CoreML 모델 로드
    let model = try! GruModel(configuration: MLModelConfiguration())
    
    var maxItem = 0
    var minItem = 0
    
    public func predict(items: [Dates]) -> [Int] {
        var result: [Int] = []
        
        // 입력 데이터 준비 (100일치 주가 데이터, 5개 특성)
        let input = try! MLMultiArray(shape: [1, 100, 5], dataType: .float32)
        
        // 데이터 정규화 (min-max scaling)
        for i in 0...99 {
            // 최대/최소값 계산 로직
            // ...
        }
        
        // 모델 입력을 위한 데이터 변환
        for i in 0...99 {
            input[i * 5]     = Float(items[i].startPrice - minItem) / Float(maxItem - minItem) as NSNumber
            input[i * 5 + 1] = Float(items[i].highPrice - minItem) / Float(maxItem - minItem) as NSNumber
            input[i * 5 + 2] = Float(items[i].lowPrice - minItem) / Float(maxItem - minItem) as NSNumber
            input[i * 5 + 3] = Float(items[i].closePrice - minItem) / Float(maxItem - minItem) as NSNumber
            input[i * 5 + 4] = Float(items[i].tradingVolume - minVolume) / Float(maxVolume - minVolume) as NSNumber
        }
        
        // GRU 모델을 사용한 초기 예측
        var gruInput = GruModelInput(x_1: input)
        var out = try! model.prediction(input: gruInput)
        
        // 30일 연속 예측 (자기회귀적 접근)
        for _ in 1...29 {
            // 데이터 시프트 (가장 오래된 데이터 제거)
            for i in 0...494 {
                input[i] = input[i+5]
            }
            
            // 예측값을 다음 입력으로 추가
            for i in 0...4 {
                input[i + 495] = out.linear_36[i]
            }
            
            // 다음 날짜 예측
            gruInput = GruModelInput(x_1: input)
            out = try! model.prediction(input: gruInput)
            
            // 역정규화 및 가격 단위 조정
            let convPrice = Float(truncating: out.linear_36[0]) * Float(maxItem - minItem) + Float(minItem)
            let convPrice2 = Int(convPrice / Float(getUnit(price: maxItem))) * getUnit(price: maxItem)
            result.append(convPrice2)
        }
        
        return result
    }
    
    // 주가 단위 계산 (거래소 규칙 반영)
    public func getUnit(price: Int) -> Int {
        if (price < 2000) { return 1 }
        else if (price < 5000) { return 5 }
        else if (price < 20000) { return 10 }
        // ...
    }
}
```

이 구현의 핵심 기술적 특징:

- Core ML 모델의 효율적인 통합
- 시계열 데이터 처리를 위한 자기회귀적 접근법
- 데이터 정규화 및 역정규화 처리

### 4. UI 컴포넌트 구현 (Compositional Layout + DiffableDataSource)

```swift
final class StockDetailViewController: UIViewController {
    // 섹션 및 아이템 정의
    fileprivate enum Section {
        case chart
        case button
        case selected
        // ...
    }
    
    fileprivate enum Item: Hashable {
        case chart(PeriodPrice)
        case predChart(PredictData)
        case button
    }
    
    private var dataSource: UICollectionViewDiffableDataSource<Section, Item>?
    
    // UI 컴포넌트 설정
    private func createLayout() -> UICollectionViewCompositionalLayout {
        return UICollectionViewCompositionalLayout(sectionProvider: {[weak self] sectionIndex, _ in
            let section = self?.dataSource?.sectionIdentifier(for: sectionIndex)
            
            switch section {
            case .chart:
                return self?.createChartSection()
            case .button:
                return self?.createButtonSection()
            // ...
            }
        }, configuration: config)
    }
    
    private func createChartSection() -> NSCollectionLayoutSection {
        let itemSize = NSCollectionLayoutSize(
            widthDimension: .fractionalWidth(1.0), 
            heightDimension: .absolute(250)
        )
        let item = NSCollectionLayoutItem(layoutSize: itemSize)
        
        let groupSize = NSCollectionLayoutSize(
            widthDimension: .fractionalWidth(1.0), 
            heightDimension: .absolute(250)
        )
        let group = NSCollectionLayoutGroup.horizontal(layoutSize: groupSize, subitems: [item])
        
        let section = NSCollectionLayoutSection(group: group)
        section.contentInsets = .init(top: 0, leading: 30, bottom: 10, trailing: 30)
        return section
    }
    
    // DiffableDataSource 설정
    private func setDataSource() {
        dataSource = UICollectionViewDiffableDataSource<Section, Item>(
            collectionView: collectionView, 
            cellProvider: { collectionView, indexPath, itemIdentifier in
                switch itemIdentifier {
                case .chart(let itemData):
                    let cell = collectionView.dequeueReusableCell(
                        withReuseIdentifier: ChartCollectionViewCell.id, 
                        for: indexPath
                    ) as? ChartCollectionViewCell
                    cell?.configure(items: itemData.periodPrice, page: "info")
                    return cell
                case .predChart(let itemData):
                    // AI 예측 차트 표시
                    // ...
                case .button:
                    // 버튼 셀 구성
                    // ...
                }
            }
        )
    }
    
    // ViewModel 바인딩
    private func bindViewModel() {
        let input = StockDetailViewModel.Input(
            stockName: self.stockName, 
            aipredictTrigger: aipredictShowTrigger
        )
        let output = viewModel.transform(input: input)
        
        // 주식 정보 업데이트
        output.stockItem.bind {[weak self] stockItem in
            var snapshot = NSDiffableDataSourceSnapshot<Section, Item>()
            
            let chart = Item.chart(stockItem)
            let chartSection = Section.chart
            snapshot.appendSections([chartSection])
            snapshot.appendItems([chart], toSection: chartSection)
            
            // ... 추가 UI 업데이트 로직
            
            self?.dataSource?.apply(snapshot)
        }.disposed(by: disposeBag)
        
        // AI 예측 결과 업데이트
        output.predItem.bind {[weak self] item in
            // ... 예측 데이터 스냅샷 구성
        }.disposed(by: disposeBag)
    }
}
```

이 UI 구현의 기술적 강점:

- 선언적 UI 레이아웃 구성
- DiffableDataSource를 활용한 효율적인 UI 업데이트
- Section과 Item enum을 통한 타입 안전성 보장
- RxSwift 바인딩을 통한 데이터 흐름 관리

### 5. 도메인 모델링 (엄격한 타입 안전성)

```swift
struct StockDetail: Decodable, Hashable {
    let stockName: String
    let logoImage: String
    let currentPrice: Int
    let priceChange: Int
    let priceChangeRate: Float
    let periodPrice: [Dates]
    
    // CodingKeys로 서버 응답과 매핑
    private enum CodingKeys: String, CodingKey {
        case stockName
        case logoImage
        case currentPrice
        case priceChange
        case priceChangeRate
        case periodPrice
    }
    
    // 커스텀 디코딩 로직
    init(from decoder: any Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        self.stockName = try container.decode(String.self, forKey: .stockName)
        self.logoImage = try container.decodeIfPresent(String.self, forKey: .logoImage) ?? "default_logo"
        self.currentPrice = try container.decode(Int.self, forKey: .currentPrice)
        self.priceChange = try container.decode(Int.self, forKey: .priceChange)
        self.priceChangeRate = try container.decode(Float.self, forKey: .priceChangeRate)
        self.periodPrice = try container.decode([Dates].self, forKey: .periodPrice)
    }
    
    // 기본값 생성자 (에러 처리용)
    init() {
        self.stockName = ""
        self.logoImage = "default_logo"
        self.currentPrice = 0
        self.priceChange = 0
        self.priceChangeRate = 0.0
        self.periodPrice = []
    }
}
```

엄격한 도메인 모델링의 이점:

- Decodable 프로토콜을 통한 자동 디코딩
- 옵셔널 필드에 대한 안전한 처리
- 기본값 생성자를 통한 오류 복원력
- Hashable 프로토콜로 DiffableDataSource 호환성 제공

## 🏗️ 설치 및 사용 방법

### 개발 환경 설정

1. 저장소 클론
```bash
git clone https://github.com/CSID-DGU/2024-1-CECD4-TheRed-4.git
```

2. Swift Package Manager 의존성 설치
```swift
// Package.swift (SPM 의존성)
dependencies: [
    .package(url: "https://github.com/ReactiveX/RxSwift.git", .upToNextMajor(from: "6.0.0")),
    .package(url: "https://github.com/RxSwiftCommunity/RxAlamofire.git", .upToNextMajor(from: "6.0.0")),
    .package(url: "https://github.com/SnapKit/SnapKit.git", .upToNextMajor(from: "5.0.0"))
]
```

## 👥 팀 구성

| 팀 | 이름 | 전공 | 역할  | 깃허브 아이디 |
|----| ----- | ----- | -------- | ------- |
| 팀장 | 유수민 | 컴퓨터공학전공 | 백엔드 개발  | proysm |
| 팀원 | 홍원준 | 컴퓨터공학전공 | 백엔드 개발  | price126 |
| 팀원 | 신예빈 | 컴퓨터공학전공 | iOS 개발  | ShinYEB |
| 팀원 | 김윤서 | 경영학과 | Android 개발  | yunssup |

## 📸 스크린샷 & 데모


[🔗 데모 영상 확인하기](https://proysm.notion.site/DEMO-154b4ca715b080b2ad95d1168621a154?pvs=4)

## 📃 라이센스

이 프로젝트는 MIT 라이센스를 따릅니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참고하세요.
