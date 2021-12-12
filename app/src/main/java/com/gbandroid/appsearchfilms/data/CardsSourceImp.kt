package com.gbandroid.appsearchfilms.data

class CardsSourceImpl() : CardsSource {
    private val dataSource: MutableList<CardFilm>

    init {
        dataSource = mutableListOf(
            CardFilm("pic_1", "Film_1", "1998", "6.7"),
            CardFilm("pic_2", "Film_2", "1999", "7.0"),
            CardFilm("pic_3", "Film_3", "2000", "5.5"),
            CardFilm("pic_4", "Film_4", "2001", "4.5"),
            CardFilm("pic_5", "Film_5", "2002", "8.1"),
            CardFilm("pic_6", "Film_6", "2003", "7.6"),
            CardFilm("pic_7", "Film_7", "2004", "3.0"),
            CardFilm("pic_8", "Film_8", "2005", "6.3"),
            CardFilm("pic_9", "Film_9", "2006", "5.8"),
            CardFilm("pic_10", "Film_10", "2007", "9.0"),
        )
    }

    override fun getCardFilm(position: Int): CardFilm {
        return dataSource[position]
    }

    override fun size(): Int {
        return dataSource.size
    }
}