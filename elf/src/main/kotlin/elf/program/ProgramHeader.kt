package elf.program

import base.IOStream
import elf.ElfFile
import elf.ElfFile.Companion.readInfo
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ProgramHeader(byteBuffer: ByteBuffer) : IOStream {
    /* 段类型 */
    val p_type: ProgramTypes

    /* 段标志 */
    val p_flags: Int

    /* 段文件偏移量 */
    val p_offset: Long

    /* 段虚拟地址 */
    val p_vaddr: Long

    /* 段物理地址 */
    val p_paddr: Long

    /* 段在文件中的大小 */
    val p_filesz: Long

    /* 段在内存中的大小 */
    val p_memsz: Long

    /* 段对齐 */
    val p_align: Long

    val start: Int
    val end: Int

    init {
        start = byteBuffer.position()
        p_type = ProgramTypes[byteBuffer.int]
        p_flags = byteBuffer.int
        p_offset = byteBuffer.long
        p_vaddr = byteBuffer.long
        p_paddr = byteBuffer.long
        p_filesz = byteBuffer.long
        p_memsz = byteBuffer.long
        p_align = byteBuffer.long
        ElfFile.readCount += 56
        end = byteBuffer.position()
        readInfo += Triple(
            start,
            0,
            "ProgramHeader[$p_type] \t [${start.toString(16)} ~ ${end.toString(16)}]"
        )
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(56)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putInt(p_type.value)
        byteBuffer.putInt(p_flags)
        byteBuffer.putLong(p_offset)
        byteBuffer.putLong(p_vaddr)
        byteBuffer.putLong(p_paddr)
        byteBuffer.putLong(p_filesz)
        byteBuffer.putLong(p_memsz)
        byteBuffer.putLong(p_align)
        return byteBuffer.array()
    }

    override fun toString(): String {
        return """${"\n"} Program64Header(
        p_type $p_type
        p_flags ${p_flags} ${ProgramFlags.getStringBy(p_flags)}
        p_offset $p_offset
        p_vaddr $p_vaddr
        p_paddr $p_paddr
        p_filesz $p_filesz
        p_memsz $p_memsz
        p_align ${p_align})
        """
    }
}

