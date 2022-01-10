package com.mny.wan.pkg.data.local

import android.text.TextUtils
import androidx.annotation.WorkerThread
import com.blankj.utilcode.util.CacheDiskUtils
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.JsonParser
import com.mojito.common.data.local.UserHelper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * WanCache
 * Desc:
 */
@Singleton
class WanCache @Inject constructor() {
    companion object {
        private const val MAX_SIZE = 10 * 1024 * 1024L
        private const val CACHE_DIR = "wan-cache"
    }

    private val mGson = GsonUtils.getGson()
    private val mCache = CacheDiskUtils.getInstance(CACHE_DIR, MAX_SIZE, Int.MAX_VALUE)

    fun isSame(cache: Any, net: Any) = TextUtils.equals(mGson.toJson(cache), mGson.toJson(net))

    @WorkerThread
    fun put(key: String, bean: Any) {
        mCache.put(EncryptUtils.encryptMD5ToString(key), mGson.toJson(bean))
    }

    @WorkerThread
    fun <T : Any> get(key: String, clazz: Class<T>): T? {
        val json = mCache.getString(EncryptUtils.encryptMD5ToString(key))
        return mGson.fromJson(json, clazz)
    }

    @WorkerThread
    fun <T : Any> getList(key: String, clazz: Class<T>): List<T> {
        val json = mCache.getString(EncryptUtils.encryptMD5ToString(key))
        if (json.isNullOrEmpty()) return emptyList()
        val list = mutableListOf<T>()
        val jsonArray = JsonParser.parseString(json).asJsonArray
        for (element in jsonArray) {
            list.add(mGson.fromJson(element, clazz))
        }
        return list
    }

    @WorkerThread
    fun remove(key: String) {
        mCache.remove(key)
    }

    object CacheKey {
        const val WX_ARTICLE_CHAPTERS = "wxarticle/chapters/json"
        private const val WX_ARTICLE_LIST = "wxarticle/list/%d/%d/json" //id+page
        private const val WX_ARTICLE_LIST_SEARCH = "wxarticle/list/%d/%d/json?key=%s" //id+page+key
        const val PROJECT_CHAPTERS = "project/tree/json"
        private const val PROJECT_ARTICLE_LIST = "project/list/%d/json?cid=%d" //page+id
        const val TOP_ARTICLE_LIST = "article/top/json"
        private const val ARTICLE_LIST = "article/list/%d/json" //page
        private const val QUESTION_LIST = "wenda/list/%d/json" //page
        const val BANNER = "banner/json"
        const val USEFUL_WEB_LIST = "friend/json"
        const val HOT_KEY_LIST = "hotkey/json"
        private const val SEARCH = "article/query/%d/json?key=%s" //page+key
        const val NAVI_LIST = "navi/json"
        const val KNOWLEDGE_LIST = "tree/json"
        private const val KNOWLEDGE_ARTICLE_LIST = "article/list/%d/json?cid=%d" //page+id
        private const val COLLECT_ARTICLE_LIST = "lg/collect/list/%d/json" //page
        private const val COLLECT_LINK_LIST = "lg/collect/usertools/json"
        private const val USER_ARTICLE_LIST = "user_article/list/%d/json" //page
        private const val USER_PAGE = "user/%d/share_articles/%d/json" //userId+page
        private const val MINE_SHARE_ARTICLE_LIST = "user/lg/private_articles/%d/json" //page
        private fun addUserId(key: String): String {
            val userId: Int = UserHelper.user?.id ?: 0
            return "$userId@$key"
        }

        fun WXARTICLE_LIST(id: Int, page: Int): String {
            return String.format(WX_ARTICLE_LIST, id, page)
        }

        fun WXARTICLE_LIST_SEARCH(id: Int, page: Int, key: String): String {
            return String.format(WX_ARTICLE_LIST_SEARCH, id, page, key)
        }

        fun PROJECT_ARTICLE_LIST(id: Int, page: Int): String {
            return String.format(PROJECT_ARTICLE_LIST, page, id)
        }

        fun SEARCH(key: String, page: Int): String {
            return String.format(SEARCH, page, key)
        }

        fun ARTICLE_LIST(page: Int): String {
            return String.format(ARTICLE_LIST, page)
        }

        fun KNOWLEDGE_ARTICLE_LIST(id: Int, page: Int): String {
            return String.format(KNOWLEDGE_ARTICLE_LIST, page, id)
        }

        fun COLLECT_ARTICLE_LIST(page: Int): String {
            return addUserId(String.format(COLLECT_ARTICLE_LIST, page))
        }

        fun COLLECT_LINK_LIST(): String {
            return addUserId(COLLECT_LINK_LIST)
        }

        fun USER_ARTICLE_LIST(page: Int): String {
            return String.format(USER_ARTICLE_LIST, page)
        }

        fun QUESTION_LIST(page: Int): String {
            return String.format(QUESTION_LIST, page)
        }

        fun USER_PAGE(userId: Int, page: Int): String {
            return String.format(USER_PAGE, userId, page)
        }

        fun MINE_SHARE_ARTICLE_LIST(page: Int): String {
            return addUserId(String.format(MINE_SHARE_ARTICLE_LIST, page))
        }
    }

}