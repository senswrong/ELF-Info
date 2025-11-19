package elf.section.content

import kotlin.experimental.and

//STT_*
enum class SymbolType(val value: Byte) {
    NOTYPE(0),/* 符号类型未指定 */
    OBJECT(1),/* 符号是数据对象 */
    FUNC(2),/* 符号是代码对象 */
    SECTION(3),/* 符号与节相关联 */
    FILE(4),/* 符号名称是文件名 */
    COMMON(5),/* 符号是公共数据对象 */
    TLS(6),/* 符号是线程局部数据对象 */
    NUM(7),/* 已定义类型的数量。 */
    LOOS(10), /* 操作系统特定的开始 */
    GNU_IFUNC(10), /* 符号是间接代码对象 */
    HIOS(12), /* 操作系统特定的结束 */
    LOPROC(13), /* 处理器特定的开始 */
    HIPROC(15), /* 处理器特定的结束 */
    ;

    companion object {
        fun get(value: Byte) = SymbolType.values().find { it.value == value and 0xf }!!
    }
}