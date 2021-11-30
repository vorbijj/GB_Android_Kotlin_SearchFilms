package com.gbandroid.appsearchfilms.data

import android.content.res.Resources
import com.gbandroid.appsearchfilms.R
import java.util.*

class CardsSourceImpl(resources: Resources) : CardsSource {
    private val dataSource: MutableList<CardData>
    private val resources: Resources

    init {
        dataSource = ArrayList(10)
        this.resources = resources
    }

    fun init(): CardsSourceImpl {
        val picture: IntArray = getImageArray()
        val name = resources.getStringArray(R.array.name_list)
        val year = resources.getStringArray(R.array.year_list)
        val rating = resources.getStringArray(R.array.rating_list)
        for (i in name.indices) {
            dataSource.add(CardData(picture[i], name[i], year[i], rating[i]))
        }
        return this
    }

    private fun getImageArray(): IntArray {
        val pictures = resources.obtainTypedArray(R.array.pic_list)
        val length = pictures.length()
        val answer = IntArray(length)
        for (i in answer.indices) {
            answer[i] = pictures.getResourceId(i, 0)
        }
        return answer
    }

    override fun getCardData(position: Int): CardData {
        return dataSource[position]
    }

    override fun size(): Int {
        return dataSource.size
    }
}