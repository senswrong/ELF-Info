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
        const val NOTE_GNU_BUILD_ID = ".note.gnu.build-id"
        const val DYNSYM = ".dynsym"
        const val GNU_VERSION = ".gnu.version"
        const val GNU_VERSION_R = ".gnu.version_r"
        const val GNU_HASH = ".gnu.hash"
        const val DYNSTR = ".dynstr"
        const val REL_TEXT = ".rel.text"
        const val REL_DATA = ".rel.data"
        const val RELA_TEXT = ".rela.text"
        const val RELA_DATA = ".rela.data"
        const val RELA_DYN = ".rela.dyn"
        const val RELA_PLT = ".rela.plt"
        const val GCC_EXCEPT_TABLE = ".gcc_except_table"
        const val RODATA = ".rodata"
        const val EH_FRAME_HDR = ".eh_frame_hdr"
        const val EH_FRAME = ".eh_frame"
        const val TEXT = ".text"
        const val PLT = ".plt"
        const val DATA_REL_RO = ".data.rel.ro"
        const val FINI_ARRAY = ".fini_array"
        const val INIT_ARRAY = ".init_array"
        const val DYNAMIC = ".dynamic"
        const val GOT = ".got"
        const val GOT_PLT = ".got.plt"
        const val DATA = ".data"
        const val BSS = ".bss"
        const val COMMENT = ".comment"
        const val SHSTRTAB = ".shstrtab"
        const val STRTAB = ".strtab"
        const val SYMTAB = ".symtab"
    }
}