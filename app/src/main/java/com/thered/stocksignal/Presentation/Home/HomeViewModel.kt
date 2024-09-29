package com.thered.stocksignal.Presentation.Home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private var tempData =  listOf(listOf("삼성전자", "64,200", "-0.77%", "https://openchat-phinf.pstatic.net/MjAyMzExMjlfMTM0/MDAxNzAxMjE4OTc1OTcz.FmWyTUNBX_8Zq0XSsr5oHK5TaLQtbFecwnskVcGSSlEg.13XMoPlR2bY5SeOJFdmJRYkrhPofPtTaOKoZbhCGwOQg.PNG/jZhwE4H5QsyHlQobWR97Ng.png"),
                            listOf("SK하이닉스", "183,800", "+1.60%", "https://openchat-phinf.pstatic.net/MjAyMzExMjdfMjQz/MDAxNzAxMDYxMzE3MTM4.MnBMnvN5w2c3yG-Pb8YKFDA7qA-UmUrciwcBhTh5E1Ag.bSKozpjqmPvhMTcmk1VjadrWXOm3huFB6klPc4d_LOgg.PNG/tYmqjXjmQeOabTfDXPc4og.png"),
                            listOf("현대차", "254,500", "-1.74%", "https://openchat-phinf.pstatic.net/MjAyMzExMjdfMzUg/MDAxNzAxMDYxMzE1ODU2.8P82Hq5f4ep6wVd20itar8WSREap-NVZbkpwvbwaqqIg.kck1OyGJQRTQRaH4X7V2i58MIUQqCKvEX-cfkmff3FQg.PNG/0WmAvvAqQF6D5XNHGVcOgw.png"),
                            listOf("롯데칠성", "131,900", "-1.86%", "https://openchat-phinf.pstatic.net/MjAyNDAyMjhfMTA3/MDAxNzA5MDk1Mjk2ODc3.IpOV6n9nCOprFq9v40YhYfvb_ncS7R1xmyajw6ucz8Qg.yvW55wyQpl68KtPu4i_ykNvZ6_KhpnzbkzNq0w2Z-sQg.PNG/tlDLJZxGRzWWkovqVbqjfA.png"),
                            listOf("KB금융", "83,300", "+3.20%", "https://openchat-phinf.pstatic.net/MjAyMzExMjdfMTY0/MDAxNzAxMDYxMzM3NTk2.199vbvzbCGnQ2G6D6WG5kEFF_uB-tmLzAejJUuR4EJcg.1UELC1AM6RBRzf6aK14NYT60sBdiZpXBoZp8o8aPrtQg.PNG/1K66YxeaRMWtyuY9yFxd1Q.png"),
                            listOf("넥슨게임즈", "15,600", "+0.65%", "https://openchat-phinf.pstatic.net/MjAyMzExMjdfMTEz/MDAxNzAxMDYwMzY4NjAz.uTON_KBGW14j7pz-vbED0pwaViBTwGrayHMxlhIvRHEg.PfMModPo1LxuPvTwjSHiaP7gyII9V_DORYbd41_Dd1wg.PNG/2uGHZwgYRcC8yqEOzHP1Ww.png"),
                            listOf("카카오", "36,650", "-0.27%", "https://openchat-phinf.pstatic.net/MjAyMzExMjlfNiAg/MDAxNzAxMjE4OTc2MzI2.uJK3OT1Tpx8MH0_ihyQw3RnBxgZNVFRm5tJInOe-7kEg.D2uVfWAKi6-kppTChPSopsS57Oih-ZzjKXdjDSjWOw8g.PNG/Q0oF_AeQaCrHTt7Sp77YQ.png"),
                            listOf("NAVER", "170,400", "-0.29%", "https://openchat-phinf.pstatic.net/MjAyMzExMjlfMTQ3/MDAxNzAxMjE4OTc2NTU5.d54mNTOtAz4I4922724tVJlZt4TQYaZc-6Oj7KeO38cg.A6HVrLD8vS57CxPYOtgn9TOCSRKzzfCMSngLMV3BoDkg.PNG/wZm0XJGhQim8uS6abk2jQ.png"),
                            listOf("하이브", "172,900", "+1.77%", "https://openchat-phinf.pstatic.net/MjAyMzExMjdfODkg/MDAxNzAxMDYxMzIxMzQ1.BchonjpJZgLK1nBMycrZaRPVD32HgUwTEjM13J22xScg.qdU3U4d9xZgH7yOxilVMiX2kYdTYlveAMaHl27Gcq38g.PNG/poBxTYpTToQUVMxYLqow.png"),
                            listOf("에스엠", "67,800", "+2.57%", "https://openchat-phinf.pstatic.net/MjAyMzExMjdfMjMw/MDAxNzAxMDYwMzY4MDkz.Q8jcNPmt-LRSdTpdeG8OmwfPuWNK4P1_IJQ58np4cp4g.eyZm0zVkEVWDKed9Uqjf1G19BBlK26aFvjdYdhnWsVsg.PNG/aigX1LbuQUKlXTfU0HXU0w.png")
    )

    fun loadData(): List<List<String>> {
        return tempData
    }
}