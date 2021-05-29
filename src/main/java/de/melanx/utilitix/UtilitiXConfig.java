package de.melanx.utilitix;

import com.google.common.collect.ImmutableList;
import de.melanx.utilitix.util.ArmorStandRotation;
import io.github.noeppi_noeppi.libx.config.Config;
import io.github.noeppi_noeppi.libx.config.Group;
import io.github.noeppi_noeppi.libx.util.ResourceList;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class UtilitiXConfig {

    @Group("Config values for the two bells, mob bell and hand bell")
    public static class HandBells {

        @Config(value = "Entity blacklist for mob bell", elementType = ResourceLocation.class)
        public static List<ResourceLocation> blacklist = ImmutableList.of();

        @Config("The time in ticks how long you have to ring the hand bell to let the mobs glow")
        public static int ringTime = 40;

        @Config("The time in ticks how long a mob should glow")
        public static int glowTime = 60;

        @Config("The radius in which entities will glow")
        public static int glowRadius = 36;

        @Config("The radius in which entities get notified that you rung")
        public static int notifyRadius = 24;
    }

    @Config(
            value = {
                    "A list of armor stand rotations for armor stands with arms.",
                    "You can cycle through these with a piece of flint."
            },
            mapper = "utilitix:armor_stand_rotation_list",
            elementType = ArmorStandRotation.class
    )
    @SuppressWarnings("configElement")
    public static List<ArmorStandRotation> armorStandPoses = ImmutableList.of(
            ArmorStandRotation.defaultRotation(),
            ArmorStandRotation.create(3.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -10.0f, 0.0f, -10.0f, -15.0f, 0.0f, 10.0f, 25.0f, 0.0f, -1.0f, -25.0f, 0.0f, 1.0f),
            ArmorStandRotation.create(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -20.0f, 0.0f, -10.0f, -85.0f, 0.0f, 0.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f),
            ArmorStandRotation.create(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -50.0f, 0.0f, 60.0f, -60.0f, -40.0f, 0.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f),
            ArmorStandRotation.create(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -10.0f, 0.0f, -110.0f, -15.0f, 0.0f, 110.0f, -1.0f, 0.0f, -15.0f, 1.0f, 0.0f, 15.0f),
            ArmorStandRotation.create(70.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, -10.0f, 0.0f, 5.0f, -15.0f, 0.0f, -5.0f, 3.0f, 0.0f, -1.0f, 3.0f, 0.0f, 1.0f),
            ArmorStandRotation.create(0.0f, -35.0f, -5.0f, 0.0f, 0.0f, 0.0f, -10.0f, 0.0f, -10.0f, -15.0f, 0.0f, 10.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f),
            ArmorStandRotation.create(0.0f, 35.0f, 5.0f, 0.0f, 0.0f, 0.0f, -10.0f, 0.0f, -10.0f, -15.0f, 0.0f, 10.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f),
            ArmorStandRotation.create(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -10.0f, 0.0f, -10.0f, -40.0f, 0.0f, 55.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f)
    );

    @Config("Items in world which have mending collect xp orbs to get repaired")
    public static boolean betterMending = true;

    @Config("List of items which are allowed to be planted when despawn on correct soil")
    public static ResourceList plantsOnDespawn = ResourceList.BLACKLIST;
}
