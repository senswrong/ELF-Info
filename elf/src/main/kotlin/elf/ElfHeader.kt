package elf

import base.IOStream
import elf.ElfFile.Companion.readInfo
import ex.byte
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ElfHeader(byteBuffer: ByteBuffer) : IOStream {
    //魔数 0X7F 0x45 0x4C 0x46
    val ei_magic: ByteArray = ByteArray(4)

    //1.32位 2.64位
    val ei_class: Byte

    //1.小端编码
    val ei_data: Byte

    //elf版本
    val ei_version: Byte

    //目标系统ABI
    val ei_os_abi: Byte

    //ABI版本
    val ei_abi_version: Byte

    //保留字节
    val ei_padding: ByteArray = ByteArray(7)

    /* 对象文件类型 */
    val e_type: ElfType

    /* 体系结构 */
    val e_machine: Short

    /* 对象文件版本 */
    val e_version: Int

    /* 入口点虚拟地址 */
    val e_entry: Long

    /* 程序头表文件偏移量 */
    val e_phoff: Long

    /* 节头表文件偏移量 */
    val e_shoff: Long

    /* 处理器特定标志 */
    val e_flags: Int

    /* ELF 头的大小（字节） */
    val e_ehsize: Short

    /* 程序头表条目大小 */
    val e_phentsize: Short

    /* 程序头表条目计数 */
    val e_phnum: Short

    /* 节头表条目大小 */
    val e_shentsize: Short

    /* 节头表条目计数 */
    val e_shnum: Short

    /* 节头字符串表索引 */
    val e_shstrndx: Short

    init {
        val start = byteBuffer.position()
        byteBuffer.get(ei_magic)
        ei_class = byteBuffer.byte
        ei_data = byteBuffer.byte
        ei_version = byteBuffer.byte
        ei_os_abi = byteBuffer.byte
        ei_abi_version = byteBuffer.byte
        byteBuffer.get(ei_padding)
        e_type = ElfType.getValueTypeBy(byteBuffer.short)
        e_machine = byteBuffer.short
        e_version = byteBuffer.int
        e_entry = byteBuffer.long
        e_phoff = byteBuffer.long
        e_shoff = byteBuffer.long

        e_flags = byteBuffer.int
        e_ehsize = byteBuffer.short
        e_phentsize = byteBuffer.short

        e_phnum = byteBuffer.short
        e_shentsize = byteBuffer.short
        e_shnum = byteBuffer.short
        e_shstrndx = byteBuffer.short

        ElfFile.readCount += 64
        readInfo += Triple(
            start,
            0,
            "ElfHeader \t [${start.toString(16)} ~ ${byteBuffer.position().toString(16)}]"
        )
    }

    override fun toBytes(): ByteArray {
        val byteBuffer = ByteBuffer.allocate(64)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.put(ei_magic)
        byteBuffer.put(ei_class)
        byteBuffer.put(ei_data)
        byteBuffer.put(ei_version)
        byteBuffer.put(ei_os_abi)
        byteBuffer.put(ei_abi_version)
        byteBuffer.put(ei_padding)
        byteBuffer.putShort(e_type.value)
        byteBuffer.putShort(e_machine)
        byteBuffer.putInt(e_version)
        byteBuffer.putLong(e_entry)
        byteBuffer.putLong(e_phoff)
        byteBuffer.putLong(e_shoff)
        byteBuffer.putInt(e_flags)
        byteBuffer.putShort(e_ehsize)
        byteBuffer.putShort(e_phentsize)
        byteBuffer.putShort(e_phnum)
        byteBuffer.putShort(e_shentsize)
        byteBuffer.putShort(e_shnum)
        byteBuffer.putShort(e_shstrndx)
        return byteBuffer.array()
    }

    override fun toString(): String {
        return """
               magic ${java.lang.String(ei_magic, "utf-8")}
               elClass $ei_class ${if (ei_class.toInt() == 1) "32 bit" else "64 bit"} 
               byteOrder $ei_data ${if (ei_data.toInt() == 1) ByteOrder.LITTLE_ENDIAN else ByteOrder.BIG_ENDIAN}
               version ${ei_version}
               osABI ${ei_os_abi}
               abiVersion ${ei_abi_version}
               e_type ${e_type}
               e_machine ${e_machine.toString(16)} ${if (e_machine.toInt() == 0xB7) "Arm 64-bits (Armv8/AArch64)" else "Unknown"})
               e_version ${e_version}
               e_entry 0x${e_entry.toString(16)}
               e_phoff 0x${e_phoff.toString(16)}
               e_shoff 0x${e_shoff.toString(16)}
               e_flags ${e_flags}
               e_ehsize ${e_ehsize}
               e_phentsize ${e_phentsize}
               e_phnum ${e_phnum}
               e_shentsize ${e_shentsize}
               e_shnum ${e_shnum}
               e_shstrndx ${e_shstrndx}
               """.trimIndent()
    }
}