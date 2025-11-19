package elf.section

//SHT_*
enum class SectionTypes(val value: Int) {
    // This value marks the section header as inactive;
    // it does not have an associated section.
    // Other members of the section header have undefined values.

    NULL(0x0),     /* Inactive section header */
    PROGBITS(0x1),     /* Information defined by the program */
    SYMTAB(0x2),     /* Symbol table - not DLL */
    STRTAB(0x3),     /* String table */
    RELA(0x4),     /* Explicit addend relocations),     Elf64_Rela */
    HASH(0x5),     /* Symbol hash table */
    DYNAMIC(0x6),     /* Information for dynamic linking */
    NOTE(0x7),     /* A Note section */
    NOBITS(0x8),     /* Like SHT_PROGBITS with no data */
    REL(0x9),     /* Implicit addend relocations),     Elf64_Rel */
    SHLIB(0xA),     /* Currently unspecified semantics */
    DYNSYM(0xB),     /* Symbol table for a DLL */
    INIT_ARRAY(0xE),     /* Array of constructors */
    FINI_ARRAY(0xF),     /* Array of deconstructors */
    PREINIT_ARRAY(0x10),     /* Array of pre-constructors */
    GROUP(0x11),     /* Section group */
    SYMTAB_SHNDX(0x12),     /* Extended section indeces */
    NUM(0x13),     /* Number of defined types */

    LOOS(0x60000000),     /* Lowest OS-specific section type */
    GNU_ATTRIBUTES(0x6ffffff5),     /* Object attribuytes */
    GNU_HASH(0x6ffffff6),     /* GNU-style hash table */
    GNU_LIBLIST(0x6ffffff7),     /* Prelink library list */
    CHECKSUM(0x6ffffff8),     /* Checksum for DSO content */
    LOSUNW(0x6ffffffa),     /* Sun-specific low bound */
    SUNW_move(0x6ffffffa),     // Same thing
    SUNW_COMDAT(0x6ffffffb),
    SUNW_syminfo(0x6ffffffc),
    GNU_verdef(0x6ffffffd),     /* Version definition section */
    GNU_verdneed(0x6ffffffe),     /* Version needs section */
    GNY_versym(0x6fffffff),     /* Version symbol table */
    HISUNW(0x6fffffff),     /* Sun-specific high bound */
    HIOS(0x6fffffff),     /* Highest OS-specific section type */
    LOPROC(0x70000000),     /* Start of processor-specific section type */
    HIPROC(0x7fffffff),     /* End of processor-specific section type */
    LOUSER(0x80000000.toInt()),     /* Start of application-specific */
    HIUSER(0x8fffffff.toInt());/* Ennd of application-specific */

    companion object {
        fun getValueTypeBy(value: Int) = SectionTypes.values().find { it.value == value }!!
    }
}