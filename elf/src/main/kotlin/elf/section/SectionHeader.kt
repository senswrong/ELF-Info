package elf.section

import base.IOStream
import elf.ElfFile
import elf.ElfFile.Companion.readInfo
import elf.section.table.BaseTable
import java.nio.ByteBuffer
import java.nio.ByteOrder

class SectionHeader(byteBuffer: ByteBuffer) : IOStream {
    var name: String = ""

    /* 节名称（字符串表索引）*/
    val sh_name: Int

    /* 节类型*/
    val sh_type: SectionTypes

    /* 节标志*/
    val sh_flags: Long

    /* 执行时的节虚拟地址*/
    val sh_addr: Long

    /* 节文件偏移量*/
    val sh_offset: Long

    /* 节大小（以字节为单位）*/
    val sh_size: Long

    /* 链接到其他节*/
    val sh_link: Int

    /* 附加节信息*/
    val sh_info: Int

    /* 节对齐方式*/
    val sh_addralign: Long

    /* 如果节包含表，则为条目大小*/
    val sh_entsize: Long

    val start: Int
    val end: Int

    var table: BaseTable? = null

    init {
        start = byteBuffer.position()
        sh_name = byteBuffer.int
        sh_type = SectionTypes.getValueTypeBy(byteBuffer.int)
        sh_flags = byteBuffer.long
        sh_addr = byteBuffer.long
        sh_offset = byteBuffer.long
        sh_size = byteBuffer.long
        sh_link = byteBuffer.int
        sh_info = byteBuffer.int
        sh_addralign = byteBuffer.long
        sh_entsize = byteBuffer.long

        ElfFile.readCount += 64
        end = byteBuffer.position()
        readInfo += Triple(
            start,
            0,
            "SectionHeader[$sh_name] \t [${start.toString(16)} ~ ${end.toString(16)}]"
        )
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(64)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putInt(sh_name)
        byteBuffer.putInt(sh_type.value)
        byteBuffer.putLong(sh_flags)
        byteBuffer.putLong(sh_addr)
        byteBuffer.putLong(sh_offset)
        byteBuffer.putLong(sh_size)
        byteBuffer.putInt(sh_link)
        byteBuffer.putInt(sh_info)
        byteBuffer.putLong(sh_addralign)
        byteBuffer.putLong(sh_entsize)
        return byteBuffer.array()
    }

    override fun toString(): String {
        return """
         sh_name $sh_name
         sh_type $sh_type
         sh_flags ${SectionFlags.getStringBy(sh_flags)}
         sh_addr $sh_addr
         sh_offset $sh_offset
         sh_size $sh_size
         sh_link $sh_link
         sh_info $sh_info
         sh_addralign $sh_addralign
         sh_entsize $sh_entsize
        """
    }
}
