package net.nanaky.villager_armour.client.models;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.nanaky.villager_armour.client.state.IHumanoidRenderState;

public class VillagerElytraModel<S extends LivingEntityRenderState> extends EntityModel<S> {
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public VillagerElytraModel(ModelPart root) {
        super(root);
        this.leftWing  = root.getChild("left_wing");
        this.rightWing = root.getChild("right_wing");
    }

    public static LayerDefinition createLayer(CubeDeformation cubeDeformation, float y, float extend) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        CubeDeformation def = new CubeDeformation(1.0F);
        root.addOrReplaceChild("left_wing",
                CubeListBuilder.create().texOffs(22, 0)
                        .addBox(-10.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, def),
                PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F,
                        (float)(Math.PI / 12), 0.0F, (float)(-Math.PI / 12)));
        root.addOrReplaceChild("right_wing",
                CubeListBuilder.create().texOffs(22, 0).mirror()
                        .addBox(0.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, def),
                PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F,
                        (float)(Math.PI / 12), 0.0F, (float)(Math.PI / 12)));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(S state) {
        super.setupAnim(state);
        IHumanoidRenderState hs = (IHumanoidRenderState) state;
        this.leftWing.y   = hs.val$isCrouching() ? 3.0F : 0.0F;
        this.leftWing.xRot = hs.val$elytraRotX();
        this.leftWing.yRot = hs.val$elytraRotY();
        this.leftWing.zRot = hs.val$elytraRotZ();
        this.rightWing.y   = this.leftWing.y;
        this.rightWing.xRot = this.leftWing.xRot;
        this.rightWing.yRot = -this.leftWing.yRot;
        this.rightWing.zRot = -this.leftWing.zRot;
    }
}