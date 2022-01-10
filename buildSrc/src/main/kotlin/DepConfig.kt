///**
// * DepConfig
// * @author caicai
// * Created on 2021-11-02 18:19
// * Desc:
// */
//class DepConfig() {
//    val isApply: Boolean = false
//    val useLocal: Boolean = false
//    val localPath: String = ""
//    val remotePath: String = ""
//    val pluginPath: String = ""
//    val pluginId: String = ""
//    var dep: String? = null
//    constructor(path: String) : this() {
//
//    }
//
//    fun getPath() = when {
//        pluginPath.isNotEmpty() -> pluginPath
//        useLocal -> localPath
//        else -> remotePath
//    }
//
//    fun getGroupId(): String? {
//        val split = remotePath.split(":")
//        return if (split.size == 3) split[0] else null
//    }
//
//    fun getArtifactId(): String? {
//        val split = remotePath.split(":")
//        return if (split.size == 3) split[1] else null
//    }
//
//    fun getVersion(): String? {
//        val split = remotePath.split(":")
//        return if (split.size == 3) split[2] else null
//    }
//
//    fun getProjectPath() = ":${localPath.substring(1).replace(":", "_")}"
//
//    override fun toString(): String {
//        return "(isApply=${getFlag(isApply)}, useLocal=${getFlag(useLocal)}, ${if (dep == null) "path = ${getPath()}" else "dep = $dep"})"
//    }
//
//
//    companion object {
//        fun getFlag(b: Boolean) = if (b) "✅" else "❌"
//    }
//
//}
