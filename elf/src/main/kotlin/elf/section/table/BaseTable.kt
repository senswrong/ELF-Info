package elf.section.table

import base.IOStream

open class BaseTable : IOStream {
    override fun toBytes(): ByteArray {
        return byteArrayOf()
    }

    companion object {
        const val SHT_NULL = ""
        const val INTERP = ".interp"
        const val NOTE_ANDROID_IDENT = ".note.android.ident"
        /*存储 ELF 文件的唯一构建标识（Build ID），用于版本匹配、调试符号关联等场景*/
        const val NOTE_GNU_BUILD_ID = ".note.gnu.build-id"
        /*存储动态链接所需的符号（如函数名、全局变量名），供运行时动态链接器查找符号地址*/
        const val DYNSYM = ".dynsym"
        const val GNU_VERSION = ".gnu.version"
        const val GNU_VERSION_R = ".gnu.version_r"
        const val GNU_HASH = ".gnu.hash"
        const val DYNSTR = ".dynstr"
        const val REL_TEXT = ".rel.text"
        const val REL_DATA = ".rel.data"
        const val RELA_TEXT = ".rela.text"
        const val RELA_DATA = ".rela.data"
        /*存储数据段（如全局变量、指针）的重定位信息，运行时动态链接器通过这些信息修正符号的实际地址*/
        const val RELA_DYN = ".rela.dyn"
        /*存储过程链接表（.plt）的重定位信息，用于延迟绑定（Lazy Binding）场景下的函数地址修正。*/
        const val RELA_PLT = ".rela.plt"
        const val GCC_EXCEPT_TABLE = ".gcc_except_table"
        const val RODATA = ".rodata"
        const val EH_FRAME_HDR = ".eh_frame_hdr"
        const val EH_FRAME = ".eh_frame"
        /*存储程序的机器指令（可执行代码），是 ELF 文件中核心的执行逻辑载体*/
        const val TEXT = ".text"
        /*实现动态链接的延迟绑定，通过跳转指令间接调用动态库函数，首次调用时触发动态链接器解析符号地址，后续直接复用已解析地址*/
        const val PLT = ".plt"
        const val DATA_REL_RO = ".data.rel.ro"
        const val FINI_ARRAY = ".fini_array"
        const val INIT_ARRAY = ".init_array"
        /*存储动态链接的核心元数据，告知动态链接器 ELF 文件的依赖库、符号表位置、重定位表位置等关键信息*/
        const val DYNAMIC = ".dynamic"
        const val GOT = ".got"
        /*全局偏移表*/
        const val GOT_PLT = ".got.plt"
        const val DATA = ".data"
        const val BSS = ".bss"
        const val COMMENT = ".comment"
        const val SHSTRTAB = ".shstrtab"
        const val STRTAB = ".strtab"
        const val SYMTAB = ".symtab"
    }
}