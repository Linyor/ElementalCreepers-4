package net.lomeli.ec.entity.explosion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class ExplosionWind {
    /** whether or not the explosion sets fire to blocks around it */
    public boolean isFlaming;

    /** whether or not this explosion spawns smoke particles */
    public boolean isSmoking = true;
    private int field_77289_h = 16;
    private Random explosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    public int explosionPower;

    /** A list of ChunkPositions of blocks affected by this explosion */
    @SuppressWarnings("rawtypes")
    public List affectedBlockPositions = new ArrayList();
    @SuppressWarnings("rawtypes")
    private Map field_77288_k = new HashMap();

    public ExplosionWind(World world, Entity entity, double xPos, double yPos, double zPos, float size, int power) {
        this.worldObj = world;
        this.exploder = entity;
        this.explosionSize = size;
        this.explosionX = xPos;
        this.explosionY = yPos;
        this.explosionZ = zPos;
        this.explosionPower = power;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void doExplosionA() {
        float f = this.explosionSize;
        int i;
        int j;
        int k;
        double d0;
        double d1;
        double d2;

        for (i = 0; i < this.field_77289_h; ++i) {
            for (j = 0; j < this.field_77289_h; ++j) {
                for (k = 0; k < this.field_77289_h; ++k) {
                    if (i == 0 || i == this.field_77289_h - 1 || j == 0 || j == this.field_77289_h - 1 || k == 0 || k == this.field_77289_h - 1) {
                        double d3 = (double) ((float) i / ((float) this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d4 = (double) ((float) j / ((float) this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d5 = (double) ((float) k / ((float) this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                        d3 /= d6;
                        d4 /= d6;
                        d5 /= d6;
                        float f1 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        d0 = this.explosionX;
                        d1 = this.explosionY;
                        d2 = this.explosionZ;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F) {
                            d0 += d3 * (double) f2;
                            d1 += d4 * (double) f2;
                            d2 += d5 * (double) f2;
                        }
                    }
                }
            }
        }

        this.explosionSize *= 2.0F;
        i = MathHelper.floor_double(this.explosionX - (double) this.explosionSize - 1.0D);
        j = MathHelper.floor_double(this.explosionX + (double) this.explosionSize + 1.0D);
        k = MathHelper.floor_double(this.explosionY - (double) this.explosionSize - 1.0D);
        int l1 = MathHelper.floor_double(this.explosionY + (double) this.explosionSize + 1.0D);
        int i2 = MathHelper.floor_double(this.explosionZ - (double) this.explosionSize - 1.0D);
        int j2 = MathHelper.floor_double(this.explosionZ + (double) this.explosionSize + 1.0D);
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder,
                AxisAlignedBB.getAABBPool().getAABB((double) i, (double) k, (double) i2, (double) j, (double) l1, (double) j2));
        Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.explosionX, this.explosionY, this.explosionZ);

        for (int k2 = 0; k2 < list.size(); ++k2) {
            Entity entity = (Entity) list.get(k2);
            double d7 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double) this.explosionSize;

            if (d7 <= 1.0D) {
                d0 = entity.posX - this.explosionX;
                d1 = entity.posY + (double) entity.getEyeHeight() - this.explosionY;
                d2 = entity.posZ - this.explosionZ;
                double d8 = (double) MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);

                if (d8 != 0.0D) {
                    d0 /= d8;
                    d1 /= d8;
                    d2 /= d8;
                    double d9 = (double) this.worldObj.getBlockDensity(vec3, entity.boundingBox);
                    double d10 = (1.0D - d7) * d9;
                    double d11 = EnchantmentProtection.func_92092_a(entity, d10);
                    entity.attackEntityFrom(DamageSource.generic, 0.0001F);
                    entity.motionX += (d0 * d11) * explosionPower;
                    entity.motionY += (d1 * d11) * 1.01;
                    entity.motionZ += (d2 * d11) * explosionPower;

                    if (entity instanceof EntityPlayer)
                        this.field_77288_k.put((EntityPlayer) entity, this.worldObj.getWorldVec3Pool().getVecFromPool(d0 * d10, d1 * d10, d2 * d10));
                }
            }
        }

        this.explosionSize = f;
    }

    @SuppressWarnings("rawtypes")
    public void doExplosionB(boolean par1) {
        if (this.explosionSize >= 2.0F && this.isSmoking)
            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        else
            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);

        Iterator iterator;
        ChunkPosition chunkposition;
        int i;
        int j;
        int k;
        int l;

        if (this.isSmoking) {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext()) {
                chunkposition = (ChunkPosition) iterator.next();
                i = chunkposition.x;
                j = chunkposition.y;
                k = chunkposition.z;
                l = this.worldObj.getBlockId(i, j, k);

                if (par1) {
                    double d0 = (double) ((float) i + this.worldObj.rand.nextFloat());
                    double d1 = (double) ((float) j + this.worldObj.rand.nextFloat());
                    double d2 = (double) ((float) k + this.worldObj.rand.nextFloat());
                    double d3 = d0 - this.explosionX;
                    double d4 = d1 - this.explosionY;
                    double d5 = d2 - this.explosionZ;
                    double d6 = (double) MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / (double) this.explosionSize + 0.1D);
                    d7 *= (double) (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.worldObj.spawnParticle("explode", (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3,
                            d4, d5);
                    this.worldObj.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
                }
            }
        }

        if (this.isFlaming) {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext()) {
                chunkposition = (ChunkPosition) iterator.next();
                i = chunkposition.x;
                j = chunkposition.y;
                k = chunkposition.z;
                l = this.worldObj.getBlockId(i, j, k);
                int i1 = this.worldObj.getBlockId(i, j - 1, k);

                if (l == 0 && Block.opaqueCubeLookup[i1] && this.explosionRNG.nextInt(3) == 0)
                    this.worldObj.setBlock(i, j, k, Block.fire.blockID);
            }
        }
    }

}
