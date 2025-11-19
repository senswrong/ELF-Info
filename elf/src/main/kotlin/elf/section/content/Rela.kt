package elf.section.content

import base.IOStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Rela(byteBuffer: ByteBuffer) : IOStream {
    val r_offset: Long/*需要重定位的地址（在 ELF 文件中的偏移或虚拟地址）*/
    val r_info: Long/*符号索引（关联.dynsym 中的符号）和重定位类型（如 R_X86_64_RELATIVE、R_X86_64_GLOB_DAT）*/
    val r_addend: Long/*重定位计算的附加数值（部分重定位类型需结合该值修正地址）*/

    init {
        r_offset = byteBuffer.long
        r_info = byteBuffer.long
        r_addend = byteBuffer.long
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(24)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putLong(r_offset)
        byteBuffer.putLong(r_info)
        byteBuffer.putLong(r_addend)
        return byteBuffer.array()
    }

    override fun toString(): String {
        return "${r_offset.toString(16)}\t${r_info.toString(16)}\t${r_addend.toString(16)}"
    }
}