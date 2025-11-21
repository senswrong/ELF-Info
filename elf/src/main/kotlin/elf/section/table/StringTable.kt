package elf.section.table

import elf.section.SectionHeader
import ex.string
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.StandardCharsets

class StringTable(override val name: String, byteBuffer: ByteBuffer, header: SectionHeader) :
    BaseDataTable(name, byteBuffer, header) {
    val indexs: MutableList<Int> = mutableListOf()
    val strings: MutableList<String> = mutableListOf()

    val subIndexs: MutableList<Int> = mutableListOf()
    val subStrings: MutableList<String> = mutableListOf()

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        while (buffer.position() < buffer.capacity()) {
            val index = buffer.position()
            val str = buffer.string
            if (str.isNotEmpty()) {
                strings += str
                val current = index// - header.sh_offset.toInt()
                indexs += current
                var subInex = str.indexOf(".", 1)
                val inds = mutableListOf<Int>()
                while (subInex > -1) {
                    subIndexs += current + subInex
                    subStrings += str.substring(subInex)
                    inds += current + subInex
                    subInex = str.indexOf(".", subInex + 1)
                }
            }
        }
    }

    fun getText(offset: Int): String {
        var index = indexs.indexOf(offset)
        if (index > -1) return strings[index]
        index = subIndexs.indexOf(offset)
        if (index > -1) return subStrings[index]
        return ""
    }

    override fun toBytes(): ByteArray {
        var count = 1
        val bytes = mutableListOf<ByteArray>()
        strings.forEach {
            val b = it.toByteArray(StandardCharsets.UTF_8)
            bytes += b
            count += b.size + 1
        }
        val byteBuffer = ByteBuffer.allocate(count)
        byteBuffer.put(0)
        bytes.forEach {
            byteBuffer.put(it)
            byteBuffer.put(0)
        }
        return byteBuffer.array()
    }
}