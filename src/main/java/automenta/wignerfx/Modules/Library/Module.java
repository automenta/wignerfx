package automenta.wignerfx.Modules.Library;

import automenta.wignerfx.Modules.Interface.Connector;
import com.gs.collections.impl.list.mutable.FastList;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class Module {
    public List<Connector> inputCompatibleConnectors = new FastList<>();
    public List<Connector> outputCompatibleConnectors = new FastList<>();

    /**
     *
     * \return the static maximal count of input/output connectors
     */
    public int getStaticMaxOfConnectors() {
        final int[] values = new int[]{inputCompatibleConnectors.size(), outputCompatibleConnectors.size()};
        return Collections.max(Arrays.asList(ArrayUtils.toObject(values)));
    }
}
