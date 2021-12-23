package com.capstone.sifood.other

import com.capstone.sifood.data.firebase.entities.Article
import com.capstone.sifood.data.local.entities.Food

object DataDummy {
    fun listArticle(): List<Article> {
        val arrayList = ArrayList<Article>()
        arrayList.add(
            Article(
                title = "Daftar Negara yang Komsumennya Makin Doyan Mie Instan RI",
                year = "2021-12-09T02:56:01Z",
                url = "https://www.viva.co.id/berita/bisnis/1430523-daftar-negara-yang-komsumennya-makin-doyan-mie-instan-ri",
                picture = "https://thumb.viva.co.id/media/frontend/thumbs3/2019/11/01/5dbc1107e5835-ilustrasi-mie-instan_665_374.jpg"
            )
        )
        return arrayList
    }

    /*fun getRemoteArticle(): List<ArticlesItem> {
        val arrayList = ArrayList<ArticlesItem>()
        arrayList.add(
            ArticlesItem(
                publishedAt = "2021-12-09T02:56:01Z",
                urlToImage = "https://thumb.viva.co.id/media/frontend/thumbs3/2019/11/01/5dbc1107e5835-ilustrasi-mie-instan_665_374.jpg",
                url = "https://www.viva.co.id/berita/bisnis/1430523-daftar-negara-yang-komsumennya-makin-doyan-mie-instan-ri",
                title = "Daftar Negara yang Komsumennya Makin Doyan Mie Instan RI"
            )
        )

        return arrayList
    }*/

    fun listFood(): List<Food> {
        val arrayList = ArrayList<Food>()
        arrayList.add(
            Food(
                imgUrl = "https://img-global.cpcdn.com/recipes/20962f941ac3a9ca/1200x630cq70/photo.jpg",
                name = "Rendang",
                province = "Sumatera Barat",
                description = "Menu yang dibuat dari olahan daging sapi dan aneka rempah ini pernah menduduki peringkat pertama dalam daftar 50 hidangan paling lezat di dunia versi CNN International, lho. Sobat Pesona harus tahu nih, ternyata aneka bumbu dan rempah yang digunakan untuk mengolah rendang merupakan bumbu yang mengandung antiseptik. Meski saat ini rendang sangat mudah dijumpai di berbagai daerah, penghasil rendang yang sangat legendaris dan terkenal kualitasnya tetap ada di Minangkabau, yakni daerah Payakumbuh.",
                popular = true
            )
        )
        return arrayList
    }
}