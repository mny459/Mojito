package com.mny.mojito.data.kv

import android.content.Context
import android.util.Log
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel

/**
 * KVHelper
 * Desc: TODO 完善高级使用
 */
class KVHelper private constructor(private val id: String, private val kvPath: String) {
    companion object {
        private const val TAG = "KVHelper"
        private const val DEFAULT_ID = "kv_helper"
        private val KV_MAP = hashMapOf<String, KVHelper>()
        private var mRootDir = ""

        fun init(context: Context) {
            val rootDir = MMKV.initialize(context, MMKVLogLevel.LevelDebug)
            mRootDir = rootDir
            Log.d(TAG, "init() called with: rootDir = $rootDir")
        }

        fun getInstance(id: String = "", rootPath: String = ""): KVHelper {
            val kvID = if (id.isEmpty()) DEFAULT_ID else id
            val kvPath = if (rootPath.isEmpty()) mRootDir else rootPath
            var kvHelper = KV_MAP[kvID]
            if (kvHelper == null) {
                synchronized(KVHelper::class.java) {
                    kvHelper = KV_MAP[kvID]
                    if (kvHelper == null) {
                        kvHelper = KVHelper(kvID, kvPath)
                        KV_MAP[kvID] = kvHelper!!
                    }
                }
            }
            return kvHelper!!
        }

        private var mKVHelper: KVHelper? = null

        fun setDefaultKV(helper: KVHelper) {
            mKVHelper = helper
        }

        private fun getDefaultKVHelper(): KVHelper {
            return if (mKVHelper == null) getInstance() else mKVHelper!!
        }

        // Boolean
        fun put(key: String, value: Boolean): Boolean {
            return getDefaultKVHelper().put(key, value)
        }

        fun getBool(key: String, defaultValue: Boolean): Boolean {
            return getDefaultKVHelper().getBool(key, defaultValue)
        }

        // Int
        fun put(key: String, value: Int): Boolean {
            return getDefaultKVHelper().put(key, value)
        }

        fun getInt(key: String, defaultValue: Int = -1): Int {
            return getDefaultKVHelper().getInt(key, defaultValue)
        }

        // Long
        fun put(key: String, value: Long): Boolean {
            return getDefaultKVHelper().put(key, value)
        }

        fun getLong(key: String, defaultValue: Long = -1L): Long {
            return getDefaultKVHelper().getLong(key, defaultValue)
        }

        // Float
        fun put(key: String, value: Float): Boolean {
            return getDefaultKVHelper().put(key, value)
        }

        fun getFloat(key: String, defaultValue: Float = -1F): Float {
            return getDefaultKVHelper().getFloat(key, defaultValue)
        }

        // Double
        fun put(key: String, value: Double): Boolean {
            return getDefaultKVHelper().put(key, value)
        }

        fun getDouble(key: String, defaultValue: Double = -1.0): Double {
            return getDefaultKVHelper().getDouble(key, defaultValue)
        }

        // String
        fun put(key: String, value: String): Boolean {
            return getDefaultKVHelper().put(key, value)
        }

        fun getString(key: String, defaultValue: String = ""): String {
            return getDefaultKVHelper().getString(key, defaultValue)
        }

        fun removeValueByKey(key: String) {
            getDefaultKVHelper().removeValueByKey(key)
        }

        fun removeValuesByKeys(arrKeys: Array<String>) {
            getDefaultKVHelper().removeValuesByKeys(arrKeys)
        }

        fun containsKey(key: String) = getDefaultKVHelper().containsKey(key)
    }

    private val mMmkv: MMKV by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        Log.d(TAG, "null() called kvPath = $kvPath id = $id")
        if (kvPath.isEmpty()) MMKV.mmkvWithID(id)
        else MMKV.mmkvWithID(id, kvPath)
    }

    // Boolean
    fun put(key: String, value: Boolean): Boolean {
        return mMmkv.encode(key, value)
    }

    fun getBool(key: String, defaultValue: Boolean): Boolean {
        return mMmkv.decodeBool(key, defaultValue)
    }

    // Int
    fun put(key: String, value: Int): Boolean {
        return mMmkv.encode(key, value)
    }

    fun getInt(key: String, defaultValue: Int = -1): Int {
        return mMmkv.decodeInt(key, defaultValue)
    }

    // Long
    fun put(key: String, value: Long): Boolean {
        return mMmkv.encode(key, value)
    }

    fun getLong(key: String, defaultValue: Long = -1L): Long {
        return mMmkv.decodeLong(key, defaultValue)
    }

    // Float
    fun put(key: String, value: Float): Boolean {
        return mMmkv.encode(key, value)
    }

    fun getFloat(key: String, defaultValue: Float = -1F): Float {
        return mMmkv.decodeFloat(key, defaultValue)
    }

    // Double
    fun put(key: String, value: Double): Boolean {
        return mMmkv.encode(key, value)
    }

    fun getDouble(key: String, defaultValue: Double = -1.0): Double {
        return mMmkv.decodeDouble(key, defaultValue)
    }

    // String
    fun put(key: String, value: String): Boolean {
        return mMmkv.encode(key, value)
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return mMmkv.decodeString(key, defaultValue) ?: defaultValue
    }

    fun removeValueByKey(key: String) {
        mMmkv.removeValueForKey(key)
    }

    fun removeValuesByKeys(arrKeys: Array<String>) {
        mMmkv.removeValuesForKeys(arrKeys)
    }

    fun containsKey(key: String) = mMmkv.containsKey(key)

}