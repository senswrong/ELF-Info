package elf.section.content

import base.IOStream
import elf.section.table.StringTable
import ex.byte
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Symbol(byteBuffer: ByteBuffer) : IOStream {
    val st_name: Int /* Symbol name (string tbl index) */
    val st_info: Byte        /* Symbol type and binding */
    val st_other: Byte        /* Symbol visibility */
    val st_shndx: Short        /* Section index */
    val st_value: Long        /* Symbol value */
    val st_size: Long        /* Symbol size */


    val bind: SymbolBind
    val type: SymbolType
    val vis: SymbolVisibility

    init {
        st_name = byteBuffer.int
        st_info = byteBuffer.byte
        st_other = byteBuffer.byte
        st_shndx = byteBuffer.short
        st_value = byteBuffer.long
        st_size = byteBuffer.long
        bind = SymbolBind.get(st_info)
        type = SymbolType.get(st_info)
        vis = SymbolVisibility.get(st_other)
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(24)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putInt(st_name)
        byteBuffer.put(st_info)
        byteBuffer.put(st_other)
        byteBuffer.putShort(st_shndx)
        byteBuffer.putLong(st_value)
        byteBuffer.putLong(st_size)
        return byteBuffer.array()
    }

    fun toString(dynString: StringTable?): String {
        return "${st_value.toString(16)}\t${st_size}\t${type}\t${bind}\t${vis}\t${st_shndx}\t${dynString?.getText(st_name)}"
    }
}