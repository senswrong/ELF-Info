package elf.section.content

//STB_*
enum class SymbolBind(val value: Byte) {
    LOCAL(0),/* 局部符号 */
    GLOBAL(1),/* 全局符号 */
    WEAK(2),/* 弱符号 */
    NUM(3),/* 已定义类型的数量。 */
    LOOS(10),/* 操作系统特定的开始 */
    GNU_UNIQUE(10),/* 唯一符号。 */
    HIOS(12),/* 操作系统特定的结束 */
    LOPROC(13),/* 处理器特定的开始 */
    HIPROC(15),/* 处理器特定的结束 */
    ;

    companion object {
        fun get(value: Byte) = SymbolBind.values().find { it.value == (value.toInt() shr 4).toByte() }!!
    }
}