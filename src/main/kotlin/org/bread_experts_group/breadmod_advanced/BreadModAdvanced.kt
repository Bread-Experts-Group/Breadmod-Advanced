package org.bread_experts_group.breadmod_advanced

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.bread_experts_group.breadmod.BreadMod
import org.bread_experts_group.breadmod_advanced.registry.ModContent
import org.bread_experts_group.upwards.UpwardsMod

@UpwardsMod(modID = BreadModAdvanced.ID, dependencyLocation = "libs", piggybackModID = BreadMod.ID)
class BreadModAdvanced(container: ModContainer, eventBus: IEventBus) {
    companion object {
        const val ID: String = "breadmod_advanced"
        private val logger: Logger = LogManager.getLogger("BreadModAdvanced Main")

//        var nowHeresTheTicker: MutableList<(LevelTickEvent) -> Boolean> = mutableListOf()
    }

//    private val sceneLock = Semaphore(1)
//    private val management: PhysXManagement
//    private var scene: PhysXScene? = null
    init {
//        val modPath = container.modInfo.owningFile.file.filePath
//        if (modPath.isDirectory()) {
//            createDirectories(Directories.modLocalData)
//            fun recursiveCopy(device: SystemDevice, into: SystemDevice) {
//                val destination = into.get(SystemDeviceFeatures.PATH_APPEND).append(
//                    device.get(SystemDeviceFeatures.PATH_ELEMENT_LAST).element
//                )
//                val status = device.get(SystemDeviceFeatures.PATH_COPY).copy(destination).start(
//                    WindowsCopySystemDeviceFeatures.DIRECTORY,
//                    WindowsCopySystemDeviceFeatures.ALLOW_UNENCRYPTED_DESTINATION,
//                    WindowsCopySystemDeviceFeatures.NO_BLOCK_DESTINATION_ENCRYPT,
//                    WindowsCopySystemDeviceFeatures.SPARSENESS
//                )
//                println("${device.get(SystemDeviceFeatures.PATH_ELEMENT_LAST).element} $status")
//                for (child in device.get(SystemDeviceFeatures.PATH_CHILDREN)) {
//                    recursiveCopy(child, destination)
//                }
//            }
//
//            SystemProvider.get(SystemFeatures.GET_PATH_DEVICE_DIRECT)
//                .get(modPath.absolutePathString())
//                .get(SystemDeviceFeatures.PATH_PARENT).parent!!
//                .get(SystemDeviceFeatures.PATH_PARENT).parent!!
//                .get(SystemDeviceFeatures.PATH_PARENT).parent!!
//                .get(SystemDeviceFeatures.PATH_APPEND).append("resources")
//                .get(SystemDeviceFeatures.PATH_APPEND).append(modPath.name)
//                .get(SystemDeviceFeatures.PATH_APPEND).append("runtimeExtracted")
//                .get(SystemDeviceFeatures.PATH_CHILDREN).forEach { child ->
//                    recursiveCopy(child, Directories.modLocalData)
//                }
//        } else TODO("COPY")

//        println("testing")
//        val kappa2 = globalArena.allocate(6999, 8)
//        kappa2.fill(-1)
//        val kappa = globalArena.allocate(8, 8)
//        kappa.set(ValueLayout.ADDRESS, 0, kappa2)
//        repeat(50000) {
//            PhysXRigidDynamic::class.java.image(nativeLinker, kappa)
//        }
//        println("Test complete")

//        management = PhysXManagement()
//        NeoForge.EVENT_BUS.addListener { event: LevelEvent.Unload ->
//            if (!event.level.isClientSide) return@addListener
//            sceneLock.acquire()
//            scene?.release()
//            scene = null
//            sceneLock.release()
//        }
//        NeoForge.EVENT_BUS.addListener { event: LevelEvent.Load ->
//            if (!event.level.isClientSide) return@addListener
//            sceneLock.acquire()
//            val dispatcher = management.physXLibrary.pxDefaultCpuDispatcherCreate(globalArena, 4u)
//            val cuda = management.physXLibrary.pxCreateCudaContextManager(
//                globalArena,
//                management.foundation, PhysXCudaContextManagerDesc()
//            )
//            val sceneDesc = PhysXSceneDesc(management.physics.getTolerancesScale())
//            sceneDesc.filterShader = MethodHandles.publicLookup().bind(
//                management.physXLibrary,
//                "pxDefaultSimulationFilterShader",
//                MethodType.methodType(
//                    MemorySegment::class.java,
//                    MemorySegment::class.java,
//                    Int::class.java, MemorySegment::class.java,
//                    Int::class.java, MemorySegment::class.java,
//                    MemorySegment::class.java,
//                    MemorySegment::class.java, Int::class.java
//                )
//            )
//            sceneDesc.cpuDispatcher = Pointer(dispatcher)
//            sceneDesc.cudaContextManager = Pointer(cuda)
//            sceneDesc.gravity = PhysXVec3T.ReadWrite(0f, -9.81f, 0f)
//            sceneDesc.flags = FlagSet.of(
//                PxSceneFlag.eENABLE_GPU_DYNAMICS,
//                PxSceneFlag.eENABLE_PCM,
//                PxSceneFlag.eENABLE_STABILIZATION
//            ).maskI.toUInt()
//            sceneDesc.broadPhaseType = PxBroadPhaseType.eGPU
//
//            val scene = management.physics.createScene(sceneDesc)
//            this.scene = scene
//            val materialPtr = management.physics.createMaterial(0.5f, 0.5f, 0.6f)
//            val groundPlane = PxCreatePlane(
//                management.physics,
//                PhysXPlane.ReadWrite(
//                    PhysXVec3T.ReadWrite(0f, 1f, 0f)
//                ),
//                materialPtr
//            )
//            scene.addActor(groundPlane)
//            var stackZ = 10f
//            val materials = autoArena.allocate(ValueLayout.ADDRESS)
//            materials.set(ValueLayout.ADDRESS, 0, materialPtr)
//            val halfExtent = 1f
//            val shape = management.physics.createShape(
//                PhysXBoxGeometry.ReadWrite(
//                    PhysXVec3T.ReadWrite(halfExtent, halfExtent, halfExtent)
//                ),
//                materials,
//                1u
//            )
//            repeat(20) {
//                val size = 50
//                val t = PhysXTransformT.ReadWrite(
//                    PxIdentityF,
//                    PhysXVec3T.ReadWrite(0f, 0f, stackZ)
//                )
//                stackZ -= 10f
//                repeat(size) { i ->
//                    repeat(size - i) { j ->
//                        val localTm = PhysXTransformT.ReadWrite(
//                            PhysXVec3T.ReadWrite(
//                                (j * 2f) - (size-i),
//                                (i * 2f + 1f),
//                                0f
//                            ) * halfExtent
//                        )
//                        val body = management.physics.createRigidDynamic(t.transform(localTm))
////                        body.attachShape(shape)
////				    	updateMassAndInertia(body, 10)
////                        scene.addActor(body)
//                    }
//                }
//            }
//            shape.release()
//
//            fun createDynamic(
//                t: PxTransform_t,
//                geometry: PhysXGeometry,
//                velocity: PxVec3_t = PhysXVec3T.ReadWrite(0f, 0f, 0f)
//            ): PhysXRigidDynamic {
//                val dynamic = PxCreateDynamic(management.physics, t, geometry, materialPtr, 10f)
//                dynamic.setAngularDamping(0.5f)
//                dynamic.setLinearVelocity(velocity)
//                scene.addActor(dynamic)
//                return dynamic
//            }
//            val ball = createDynamic(
//                PhysXTransformT.ReadWrite(
//                    PhysXVec3T.ReadWrite(0f, 20f, 100f)
//                ),
//                PhysXSphereGeometry.ReadWrite(5f),
//                PhysXVec3T.ReadWrite(0f, -25f, -100f)
//            )
//            ball.setMass(5000f)
//            // updateMassAndInertia(ball, 1000f)
//
//            nowHeresTheTicker.add { event ->
//                sceneLock.acquire()
//				val scene = this.scene
//                if (scene == null) {
//                    sceneLock.release()
//                    return@add true
//                }
//				scene.simulate(1f / 20)
//                scene.fetchResults(true)
//                sceneLock.release()
//                false
//            }
//            sceneLock.release()
//        }
//        NeoForge.EVENT_BUS.addListener { event: LevelTickEvent.Pre ->
//            if (!event.level.isClientSide) return@addListener
//            nowHeresTheTicker.removeIf { it(event) }
//        }
//        if (!FMLLoader.isProduction() || System.getProperty("breadmod.logging") == "true") {
//            val context = LogManager.getContext(false) as LoggerContext
//            val fileLocator = this::class.java.getResource("/log4j2.xml")?.toURI()
//                ?: throw IllegalStateException("Failed to load log4j2.xml")
//            val configuration = ConfigurationFactory
//                .getInstance()
//                .getConfiguration(
//                    context,
//                    context.name,
//                    fileLocator,
//                    null
//                )
//            val colorAppender = ConsoleColorAppender.createAppender("ConsoleColorAppender", null)
//            configuration.addAppender(colorAppender)
//            Configurator.reconfigure(configuration)
//        }
        logger.info("Hello from BreadModAdvanced!")
        logger.info("Registering Blocks & Items")
        for (registry in ModContent) registry.register(eventBus)
    }
}
