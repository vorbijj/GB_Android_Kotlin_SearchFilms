package com.gbandroid.appsearchfilms.domain

class CardsSourceImpl() : CardsSource {
    private val dataSource: MutableList<CardFilm>

    init {
        dataSource = mutableListOf(
            CardFilm("pic_1", "Film_1", "2021", "##", 634649L),
            CardFilm("pic_2", "Film_2", "2021", "##", 624860L),
            CardFilm("pic_3", "Film_3", "2021", "##", 425909L),
            CardFilm("pic_4", "Film_4", "2021", "##", 460458L),
            CardFilm("pic_5", "Film_5", "2021", "##", 438695L),
            CardFilm("pic_6", "Film_6", "2021", "##", 644495L),
            CardFilm("pic_7", "Film_7", "2021", "##", 568124L),
            CardFilm("pic_8", "Film_8", "2021", "##", 617653L),
            CardFilm("pic_9", "Film_9", "2021", "##", 614917L),
            CardFilm("pic_10", "Film_10", "2021", "##", 8241008784L)
        )
    }

    override fun getCardFilm(position: Int) = dataSource[position]

    override fun size() = dataSource.size
}