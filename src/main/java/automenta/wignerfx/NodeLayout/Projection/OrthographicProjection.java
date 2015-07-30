package automenta.wignerfx.NodeLayout.Projection;

import com.gs.collections.impl.list.mutable.FastList;
import org.apache.commons.math3.linear.ArrayRealVector;

import java.util.List;

/**
 *
 */
public class OrthographicProjection {
    public OrthographicProjection(final ArrayRealVector upVector, final ArrayRealVector yVector) {
        this.upVector = upVector;
        this.yVector = yVector;
    }

    public List<ArrayRealVector> project(List<ArrayRealVector> positions) {
        final ArrayRealVector sideVector = getNormalizedSideVector();

        List<ArrayRealVector> resultPositions = new FastList<>();

        for( final ArrayRealVector iterationPosition : positions ) {
            final double projectedOnYVector = yVector.dotProduct(iterationPosition);
            final double projectedOnSideVector = sideVector.dotProduct(iterationPosition);

            resultPositions.add(new ArrayRealVector(new double[]{projectedOnSideVector, projectedOnYVector}));
        }

        return resultPositions;
    }

    private ArrayRealVector getNormalizedSideVector() {
        final ArrayRealVector unnormalizedSideVector = getCrossProductFor3d(upVector, yVector);
        final ArrayRealVector normalizedSizeVector = normalize(unnormalizedSideVector);
        return normalizedSizeVector;
    }

    private static ArrayRealVector getCrossProductFor3d(final ArrayRealVector a, final ArrayRealVector b) {
        return new ArrayRealVector(new double[]{
                a.getDataRef()[1]*b.getDataRef()[2] - a.getDataRef()[2]*b.getDataRef()[1],
                a.getDataRef()[2]*b.getDataRef()[0] - a.getDataRef()[0]*b.getDataRef()[2],
                a.getDataRef()[0]*b.getDataRef()[1] - a.getDataRef()[1]*b.getDataRef()[0]
        });
    }

    private static ArrayRealVector normalize(final ArrayRealVector vector) {
        final double length = vector.getNorm();
        return new ArrayRealVector(vector.mapMultiply(1.0 / length));
    }

    public static OrthographicProjection create3dDefault() {
        return new OrthographicProjection(new ArrayRealVector(new double[]{0.0, 1.0, 0.0}), new ArrayRealVector(new double[]{0.0, 1.0, 0.0}));
    }

    public ArrayRealVector upVector, yVector;
}
