package com.stovia.cuisiner.ui.model

class Recipe (val name: String? = "recipeName"){
    var selected : Boolean = false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Recipe(name=$name, selected=$selected)"
    }

}