package com.thered.stocksignal.presentation.stockinfo

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.thered.stocksignal.Data.Network.RetrofitClient
import com.thered.stocksignal.Data.model.CompanyCodeResponse
import com.thered.stocksignal.Data.model.NewsResponse
import com.thered.stocksignal.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class NewsFragment : Fragment() {
    private lateinit var articlePreview: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articlePreview = view.findViewById(R.id.articlePreview)

        val companyName = "삼성전자" // 예시로 삼성전자 사용
        fetchCompanyCode(companyName)
    }

    // 회사명을 이용해 회사 코드를 가져오는 API 호출
    private fun fetchCompanyCode(companyName: String) {
        RetrofitClient.stockInfoApi.getCompanyCode(companyName)
            .enqueue(object : Callback<CompanyCodeResponse> {
                override fun onResponse(
                    call: Call<CompanyCodeResponse>,
                    response: Response<CompanyCodeResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val companyCode = response.body()?.data?.companyCode
                        if (companyCode != null) {
                            // 회사 코드가 성공적으로 받아졌으면, 그 코드로 뉴스를 가져옵니다.
                            fetchNews(companyCode)
                        } else {
                            articlePreview.text = "회사 코드를 가져오는데 실패했습니다."
                        }
                    } else {
                        articlePreview.text = "회사 코드를 가져오는데 실패했습니다."
                    }
                }

                override fun onFailure(call: Call<CompanyCodeResponse>, t: Throwable) {
                    articlePreview.text = "API 호출 실패: ${t.message}"
                }
            })
    }

    // 회사 코드로 뉴스를 가져오는 API 호출
    private fun fetchNews(stockCode: String) {
        RetrofitClient.newsApi.getNews(stockCode).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    if (newsResponse != null && newsResponse.code == "200" && newsResponse.result == "SUCCESS") {
                        val articles = newsResponse.data
                        if (articles.isNotEmpty()) {
                            val displayedArticles = articles.take(3)

                            val formattedText = StringBuilder()
                            for (article in displayedArticles) {
                                formattedText.append("<a href=\"${article.url}\">${article.title}</a><br>")
                            }

                            articlePreview.text =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    Html.fromHtml(
                                        formattedText.toString(),
                                        Html.FROM_HTML_MODE_COMPACT
                                    )
                                } else {
                                    Html.fromHtml(formattedText.toString())
                                }

                            articlePreview.movementMethod = LinkMovementMethod.getInstance()
                        } else {
                            articlePreview.text = "뉴스가 없습니다."
                        }
                    } else {
                        articlePreview.text = "응답 실패: ${newsResponse?.message}"
                    }
                } else {
                    articlePreview.text = "뉴스 로드 실패: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                articlePreview.text = "뉴스 로드 실패: ${t.message}"
            }
        })
    }
}