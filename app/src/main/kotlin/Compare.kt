import elf.ElfFile
import elf.section.table.*
import elf.section.table.BaseTable.Companion.DYNSTR
import elf.section.table.BaseTable.Companion.SHSTRTAB
import java.io.File

fun main(args: Array<String>) {
    val elf4File = ElfFile(File(ElfFile::class.java.classLoader.getResource("libmobileffmpeg.so").path).getData())
    val elf16File = ElfFile(File(ElfFile::class.java.classLoader.getResource("libmobileffmpeg_16.so").path).getData())
    var section16String: StringTable? = null
    var dyn16String: StringTable? = null
    elf16File.apply {
        sectionHeaders.forEachIndexed { index, sectionHeader ->
            val name = sectionHeader.name
            when (name) {
                DYNSTR -> dyn16String = sectionHeader.table as StringTable
                SHSTRTAB -> section16String = sectionHeader.table as StringTable
            }
        }
    }

    elf16File.sectionHeaders.forEachIndexed { index, section16Header ->
        val table4 = elf4File.sectionHeaders[index].table
        val table16 = section16Header.table
        if (table16 !is BaseDataTable || table4 !is BaseDataTable) return@forEachIndexed
        if (table4.data.contentEquals(table16.data)) return@forEachIndexed

        val sectionHeader = elf16File.sectionHeaders[index]
        val name = section16String?.getText(sectionHeader.sh_name)
        println("$name <> ${sectionHeader.sh_type}")
        when (table16) {
            is SymTable -> {
                println("Num:    Value          Size Type    Bind   Vis      Ndx Name")
                table16.symbols.forEachIndexed { index, symbol16 ->
                    val symbol4 = (table4 as SymTable).symbols[index]
                    if (!symbol16.toBytes().contentEquals(symbol4.toBytes())) {
                        println("$index\t${symbol4.st_value.toString(16)}\t${symbol16.toString(dyn16String)}")
                    }
                }
            }

            is RelaTable -> {
                println("  Offset          Info           Type           Sym. Value    Sym. Name + Addend")
                table16.relas.forEachIndexed { index, rela16 ->
                    val rela4 = (table4 as RelaTable).relas[index]
                    if (!rela16.toBytes().contentEquals(rela4.toBytes())) {
                        println(
                            "$index\t${rela4.r_info.toString(16)} : " +
                                    "${rela4.r_offset.toString(16)}\t${rela4.r_addend.toString(16)}" +
                                    " <> ${rela16.r_offset.toString(16)}\t${rela16.r_addend.toString(16)}"
                        )
                    }
                }
            }

            is DynamicTable -> {
                println("  Tag        Type                         Name/Value")
                table16.dynamics.forEachIndexed { index, dynamic16 ->
                    val dynamic4 = (table4 as DynamicTable).dynamics[index]
                    if (!dynamic16.toBytes().contentEquals(dynamic4.toBytes())) {
                        println("$index\t$dynamic4 <> $dynamic16")
                    }
                }
            }
        }
    }
}
