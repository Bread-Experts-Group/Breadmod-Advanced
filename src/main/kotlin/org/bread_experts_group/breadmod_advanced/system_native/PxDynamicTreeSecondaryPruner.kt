package org.bread_experts_group.breadmod_advanced.system_native

/**
 * Secondary pruning structure used for newly added objects in dynamic trees.
 *
 * Dynamic trees ([PxPruningStructureType.eDYNAMIC_AABB_TREE]) are slowly rebuilt
 * over several frames. A secondary pruning structure holds and manages objects
 * added to the scene while this rebuild is in progress.
 *
 * [eNONE] ignores newly added objects. This means that for a number of frames (roughly
 * defined by [PxSceneQueryDesc.dynamicTreeRebuildRateHint]) newly added objects will
 * be ignored by scene queries. This can be acceptable when streaming large worlds, e.g.
 * when the objects added at the boundaries of the game world don't immediately need to be
 * visible from scene queries (it would be equivalent to streaming that data in a few frames
 * later). The advantage of this approach is that there is no CPU cost associated with
 * inserting the new objects in the scene query data structures, and no extra runtime cost
 * when performing queries.
 *
 * [eBUCKET] uses a structure similar to [PxPruningStructureType.eNONE]. Insertion is fast but
 * query cost can be high.
 *
 * [eINCREMENTAL] uses an incremental AABB-tree, with no direct [PxPruningStructureType] equivalent.
 * Query time is fast but insertion cost can be high.
 *
 * [eBVH] uses a PxBVH structure. This usually offers the best overall performance.
 */
enum class PxDynamicTreeSecondaryPruner {
	/**
	 * no secondary pruner, new objects aren't visible to SQ for a few frames
	 */
	eNONE,

	/**
	 * bucket-based secondary pruner, faster updates, slower query time
	 */
	eBUCKET,

	/**
	 * incremental-BVH secondary pruner, faster query time, slower updates
	 */
	eINCREMENTAL,

	/**
	 * PxBVH-based secondary pruner, good overall performance
	 */
	eBVH
}