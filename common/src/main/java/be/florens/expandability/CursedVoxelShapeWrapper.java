package be.florens.expandability;

import be.florens.expandability.mixin.fluidcollision.VoxelShapeAccessor;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CursedVoxelShapeWrapper extends VoxelShape {

    private VoxelShape originalShape;
    private VoxelShape mergedShape;

    public CursedVoxelShapeWrapper(VoxelShape originalShape, VoxelShape mergedShape) {
        super(((VoxelShapeAccessor) mergedShape).getShape());
        this.originalShape = originalShape;
        this.mergedShape = mergedShape;
    }

    @Override
    protected DoubleList getCoords(Direction.Axis axis) {
        return ((VoxelShapeAccessor) this.mergedShape).callGetCoords(axis);
    }

    @Override
    public double collide(Direction.Axis axis, AABB aABB, double d) {
        if (axis == Direction.Axis.Y) {
            return super.collide(axis, aABB, d);
        }

        return originalShape.collide(axis, aABB, d);
    }

    @Override
    public VoxelShape move(double d, double e, double f) {
        return new CursedVoxelShapeWrapper(originalShape.move(d, e, f), mergedShape.move(d, e, f));
    }
}
