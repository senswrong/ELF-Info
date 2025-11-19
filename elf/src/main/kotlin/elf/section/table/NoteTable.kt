package elf.section.table

import elf.section.SectionHeader
import elf.section.content.Note
import java.nio.ByteBuffer
import java.nio.ByteOrder

class NoteTable(byteBuffer: ByteBuffer, sectionHeader: SectionHeader) : BaseDataTable(byteBuffer, sectionHeader) {
    val note: Note

    init {
        val buffer = ByteBuffer.wrap(data)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
//        println("size->${sectionHeader.sh_size}<>${sectionHeader.sh_entsize}")
        note = Note(buffer)
    }
}