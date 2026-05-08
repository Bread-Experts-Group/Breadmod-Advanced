@file:Suppress("ImplicitThis", "UnstableApiUsage")

import net.neoforged.moddevgradle.dsl.RunModel

plugins {
	kotlin("jvm") version "2.3.21"
	id("org.jetbrains.dokka-javadoc") version "2.1.0"
	id("idea")
	id("net.neoforged.moddev") version "2.0.134"
	`maven-publish`
	`java-library`
	signing
}

group = project.properties["mod_group_id"] as String
version = project.properties["mod_version"] as String

private fun getModId(): String = project.properties["mod_id"] as String
private fun mcVersion(): String = project.properties["minecraft_version"] as String

private val breadServerLib: String = "org.bread_experts_group:bread_server_lib-code:D1F6N8P3"
private val breadMod: String = "org.bread_experts_group:breadmod:1.6.1"
private val upwards: String = "org.bread_experts_group:upwards:4"

idea.module {
	isDownloadSources = true
	isDownloadJavadoc = true
}

base.archivesName = getModId()

repositories {
	mavenCentral()
	mavenLocal()
	maven {
		name = "JEI Maven"
		url = uri("https://maven.blamejared.com/")
	}
	maven {
		name = "JEI Backup Maven / ModMaven"
		url = uri("https://modmaven.dev")
	}
	maven {
		name = "CurseForge Maven"
		url = uri("https://www.cursemaven.com")
		content { includeGroup("curse.maven") }
	}
	maven {
		name = "Create Maven"
		url = uri("https://maven.createmod.net")
	}
	maven {
		name = "Registrate Maven"
		url = uri("https://mvn.devos.one/snapshots")
	}
	maven {
		name = "ForgeConfigAPIPort"
		url = uri("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
	}
//	maven {
//		name = "Bread Experts Group Maven"
//		url = uri("https://maven.breadexperts.group/")
//	}
}

neoForge {
	version = project.properties["neo_version"] as String

	parchment {
		minecraftVersion = mcVersion()
		mappingsVersion = "2024.11.17"
	}

	accessTransformers {
		file("src/main/resources/META-INF/accesstransformer.cfg")
	}

	runs {
		fun RunModel.enableTestNamespaces(): Unit = systemProperty(
			"neoforge.enabledGameTestNamespaces", getModId()
		)

		create("client") {
			client()
			gameDirectory.set(File("./run/client"))
			enableTestNamespaces()
			devLogin = true
		}
		create("clientNoDevLogin") {
			client()
			gameDirectory.set(File("./run/client"))
			enableTestNamespaces()
		}
		create("server") {
			server()
			programArgument("--nogui")
			gameDirectory.set(File("./run/server"))
			enableTestNamespaces()
		}
		create("data") {
			data()
			gameDirectory.set(File("./run/data"))
			programArguments.addAll(
				"--mod", getModId(),
				"--all",
				"--output", file("src/generated/resources/").absolutePath,
				"--existing", file("src/main/resources/").absolutePath
			)
		}
		configureEach {
			this.jvmArguments.addAll(
				"-javaagent:${file("build/libs/breadmod_advanced-1.0.0.jar").absolutePath}",
				"--enable-native-access=ALL-UNNAMED", // Restricted use: BSL and associates
				"--illegal-native-access=allow" // Restricted use: all other libraries
			)

			logLevel = org.slf4j.event.Level.INFO
			additionalRuntimeClasspathConfiguration.dependencies.add(
				dependencies.create(breadServerLib) { isTransitive = false }
			)
		}
	}

	mods {
		create(getModId()) {
			sourceSet(sourceSets.main.get())
		}
	}
}

private val upwardsLibraries: Configuration by configurations.creating

configurations {
	implementation.get().extendsFrom(upwardsLibraries)
}

dependencies {
	// Mod Dependencies //
	upwardsLibraries(implementation(breadServerLib) { isTransitive = false })
	implementation(upwards)
	implementation(breadMod)
	// Mod Compatibility //
	implementation("curse.maven:jade-324717:5976517")
	implementation("curse.maven:projecte-226410:6611984")
	// Just Enough Items (JEI)
	val jeiVersion = "19.21.2.313"
	compileOnly("mezz.jei:jei-${mcVersion()}-neoforge-api:${jeiVersion}")
	runtimeOnly("mezz.jei:jei-${mcVersion()}-neoforge:${jeiVersion}")
	// Mekanism
	val mekanismVersion = "${mcVersion()}-10.7.9.72"
	compileOnly("mekanism:Mekanism:${mekanismVersion}:api")
	implementation("mekanism:Mekanism:${mekanismVersion}")
	implementation("mekanism:Mekanism:${mekanismVersion}:additions")
	implementation("mekanism:Mekanism:${mekanismVersion}:generators")
	implementation("mekanism:Mekanism:${mekanismVersion}:tools")
	// WorldEdit
	runtimeOnly("curse.maven:worldedit-225608:5830452")
}
kotlin {
	jvmToolchain(25)
	compilerOptions {
		freeCompilerArgs.add("-Xcontext-parameters")
	}
}

tasks.processResources {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.register<ProcessResources>("generateModMetadata")
tasks.named<ProcessResources>("generateModMetadata") {
	val replaceProperties = mapOf(
		"minecraft_version" to "${project.properties["minecraft_version"]}",
		"minecraft_version_range" to "${project.properties["minecraft_version_range"]}",
		"neo_version" to "${project.properties["neo_version"]}",
		"neo_version_range" to "${project.properties["neo_version_range"]}",
		"mod_id" to "${project.properties["mod_id"]}",
		"mod_name" to "${project.properties["mod_name"]}",
		"mod_license" to "${project.properties["mod_license"]}",
		"mod_version" to "${project.properties["mod_version"]}",
		"mod_authors" to "${project.properties["mod_authors"]}",
		"mod_description" to "${project.properties["mod_description"]}",
	)
	inputs.properties(replaceProperties)
	expand(replaceProperties)
	from("src/main/templates/")
	into("build/generated/sources/modMetadata")
	duplicatesStrategy = DuplicatesStrategy.WARN
}

sourceSets.main.get().resources {
	srcDirs("src/generated/resources", tasks["generateModMetadata"])
}

tasks.jar {
	manifest {
		attributes(
			"Premain-Class" to "org.bread_experts_group.breadmod_advanced.preload_agent.Agent"
		)
	}
}

neoForge.ideSyncTask(
	tasks.register<Copy>("pullUpwardsLibraries") {
		dependsOn(tasks["generateModMetadata"])
		delete("./src/main/resources/libs")
		from(upwardsLibraries).into("./src/main/resources/libs")
	}
)