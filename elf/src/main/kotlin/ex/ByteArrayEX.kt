package ex
/**
 * ByteArray 转 16 进制字符串（通用扩展函数）
 * @param uppercase 是否大写（默认：true）
 * @param separator 字节间分隔符（默认：空格，传空字符串可取消分隔）
 * @param prefix 字符串前缀（默认："0x"，传空字符串可取消）
 * @param groupSize 每 N 个字节分组（默认：0 不分组，如 4 表示每4个字节分一组）
 * @param groupSeparator 分组间分隔符（默认："  "，仅 groupSize>0 时生效）
 * @return 格式化后的 16 进制字符串
 */
fun ByteArray.toHexString(
    uppercase: Boolean = true,
    separator: String = " ",
    prefix: String = "0x",
    groupSize: Int = 0,
    groupSeparator: String = "  "
): String {
    if (isEmpty()) return "$prefix[]"

    // 单个字节转 2 位 16 进制字符串
    val hexChars = if (uppercase) "0123456789ABCDEF" else "0123456789abcdef"
    val byteHexList = map { byte ->
        val unsigned = byte.toUInt() and 0xFFu // 处理负数（转为无符号字节）
        "${hexChars[(unsigned shr 4).toInt()]}" + // 高4位
                "${hexChars[(unsigned and 0x0Fu).toInt()]}" // 低4位
    }

    // 分组处理（可选）
    val groupedList = if (groupSize > 0) {
        byteHexList.chunked(groupSize) { group -> group.joinToString(separator) }
    } else {
        listOf(byteHexList.joinToString(separator))
    }

    // 拼接前缀和分组
    return prefix + groupedList.joinToString(groupSeparator)
}

/**
 * 简化版：默认格式（大写、空格分隔、0x前缀）
 */
fun ByteArray.toSimpleHex() = toHexString()

/**
 * 紧凑版：无分隔符、小写、无前缀（如用于 Build ID 打印）
 */
fun ByteArray.toCompactHex() = toHexString(
    uppercase = false,
    separator = "",
    prefix = "",
    groupSize = 0
)