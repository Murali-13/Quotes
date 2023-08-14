package com.example.quotes.repository

import com.example.quotes.api.TweetsAPI
import com.example.quotes.models.TweetListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TweetRepository @Inject constructor(private val tweetsAPI: TweetsAPI) {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>>
        get() = _categories

    private val _tweet = MutableStateFlow<List<TweetListItem>>(emptyList())
    val tweets: StateFlow<List<TweetListItem>>
        get() = _tweet

    suspend fun getCategories() {
        val response = tweetsAPI.getCategories()
        if (response.isSuccessful && response.body() != null) {
            _categories.emit(response.body()!!)
        }
    }

    suspend fun getTweets(category: String) {
        val response = tweetsAPI.getTweets("\$.tweets[?(@.category==\"$category\")]")
        if (response.isSuccessful && response.body() != null) {
            _tweet.emit(response.body()!!)
        }
    }
}