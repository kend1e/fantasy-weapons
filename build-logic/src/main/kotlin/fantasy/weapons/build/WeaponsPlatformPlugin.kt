package fantasy.weapons.build

import fantasy.gradle.Loader
import fantasy.gradle.configureModSources
import fantasy.gradle.configureModResources
import fantasy.gradle.ResourcesExtension
import fantasy.gradle.configureMod
import fantasy.weapons.definitions.content
import fantasy.weapons.definitions.languages
import fantasy.weapons.definitions.mod
import fantasy.weapons.definitions.targets
import fantasy.weapons.definitions.recipeItemsByMinecraft
import fantasy.gradle.minecraft.MinecraftVersions
import fantasy.gradle.content.weapons.contentResources
import fantasy.gradle.ExportRelease
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.*

class WeaponsPlatformPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val target = targets.single { it.path == path }
        configureMod(mod, target)
        pluginManager.apply("fantasy.build.${target.loader.id}")
        configureModSources(if (target.minecraft == "1.12.2" ||
            target.loader == Loader.FABRIC && target.minecraft in setOf("1.14.4", "1.15.2")) emptyList() else listOf(
            "minecraft/${target.minecraft}/src/main/java",
            "loaders/${target.loader.id}/src/main/java",
        ))
        configureModResources()
        extensions.configure<ResourcesExtension> {
            generate("generateContentResources") {
                contents.set(contentResources(content, languages, target.minecraft,
                    recipeItemsByMinecraft[target.minecraft].orEmpty()))
                outputDirectory.set(layout.buildDirectory.dir("generated/resources/content"))
            }
        }
        repositories.mavenCentral()
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies.add("testImplementation", libs.findLibrary("junit").get())
        if (MinecraftVersions[target.minecraft].loader(target.loader).tests != null) {
            configureWeaponsTests()
        } else tasks.named("test") {
            description = "No native GameTest runner is available for ${target.name}; verify this platform in Minecraft."
        }
        if (target.loader == Loader.FORGE) extensions.configure<PublishingExtension> {
            val release = tasks.named<ExportRelease>("exportRelease")
            publications.register<MavenPublication>("mavenJava") {
                artifactId = mod.id
                artifact(release.flatMap { it.destination }) { builtBy(release) }
            }
            repositories.maven { url = layout.projectDirectory.dir("mcmodsrepo").asFile.toURI() }
        }
    }
}
