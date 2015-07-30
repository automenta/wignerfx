package automenta.wignerfx.NodeLayout;

import org.apache.commons.math3.linear.ArrayRealVector;

import java.util.List;

/**
 * Layouts the nodes as if they are particles.
 *
 * The Nodes/particles can have attached forces (from GUI) and are attacted/repelled from/to other nodes
 */
public class ParticleLayout {
    public static class NodeParticle {
        public ArrayRealVector position;
        public ArrayRealVector velocity;
    }

    public void layout(final List<NodeParticle> nodeParticles, float timeDelta) {
        for( final NodeParticle iterationParticle : nodeParticles ) {
            iterationParticle.position = iterationParticle.position.add(iterationParticle.velocity.mapMultiply(timeDelta));
        }

        // TODO< transfer forces between connected nodes >
    }

    // TODO< graph of connections >
}
