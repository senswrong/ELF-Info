package elf.section.content

//STV_*
enum class SymbolVisibility(val value: Byte) {
    DEFAULT(0),/* 默认符号可见性规则 */
    INTERNAL(1),/* 处理器特定的隐藏类 */
    HIDDEN(2),/* 在其他模块中不可用的符号 */
    PROTECTED(3),/* 不可抢占，不导出 */
    ;

    companion object {
        fun get(value: Byte) = SymbolVisibility.values().find { it.value == value }!!
    }
}