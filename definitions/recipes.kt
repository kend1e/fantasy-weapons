package fantasy.weapons.definitions

import fantasy.gradle.minecraft.RecipeItem

private val sharedRecipeReplacements = mapOf(
    "minecraft:netherite_sword" to RecipeItem("minecraft:diamond_sword"),
    "minecraft:honeycomb" to RecipeItem("minecraft:yellow_dye"),
    "minecraft:raw_copper" to RecipeItem("minecraft:brick"),
    "minecraft:echo_shard" to RecipeItem("minecraft:end_crystal"),
    "minecraft:cherry_log" to RecipeItem("minecraft:acacia_log"),
    "minecraft:soul_lantern" to RecipeItem("minecraft:glowstone"),
    "minecraft:amethyst_shard" to RecipeItem("minecraft:magenta_dye"),
    "minecraft:basalt" to RecipeItem("minecraft:netherrack"),
    "minecraft:soul_torch" to RecipeItem("minecraft:redstone_torch"),
)

val recipeItemsByMinecraft = mapOf(
    "1.14.4" to sharedRecipeReplacements,
    "1.15.2" to sharedRecipeReplacements - "minecraft:honeycomb",
    "1.16.5" to sharedRecipeReplacements.filterKeys { it in setOf(
        "minecraft:raw_copper", "minecraft:echo_shard", "minecraft:cherry_log", "minecraft:amethyst_shard",
    ) },
    "1.18.2" to sharedRecipeReplacements.filterKeys { it in setOf("minecraft:echo_shard", "minecraft:cherry_log") },
    "1.19.2" to sharedRecipeReplacements.filterKeys { it == "minecraft:cherry_log" },
    "1.19.3" to sharedRecipeReplacements.filterKeys { it == "minecraft:cherry_log" },
    "1.12.2" to sharedRecipeReplacements + mapOf(
        "minecraft:nautilus_shell" to RecipeItem("minecraft:shulker_shell"),
        "minecraft:bamboo" to RecipeItem("minecraft:cactus"),
        "minecraft:blue_ice" to RecipeItem("minecraft:ice"),
        "minecraft:bell" to RecipeItem("minecraft:light_weighted_pressure_plate"),
        "minecraft:sea_pickle" to RecipeItem("minecraft:sea_lantern"),
        "minecraft:lily_of_the_valley" to RecipeItem("minecraft:white_tulip"),
        "minecraft:phantom_membrane" to RecipeItem("minecraft:rabbit_hide"),
        "minecraft:dried_kelp" to RecipeItem("minecraft:beetroot_seeds"),
    ),
)
