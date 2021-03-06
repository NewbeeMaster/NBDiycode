package newbeemaster.com.nbdiycode.config.zrefresh;

import zone.com.zrefreshlayout.IResistance;

/**
 * Created by fuzhipeng on 2017/1/10.
 */

public class DampingTo3Head implements IResistance {

    @Override
    public IResistance clone_() {
        DampingTo3Head clone_ = new DampingTo3Head();
        return clone_;
    }

    @Override
    public int getOffSetYMapValue(int headerHeight, int offset) {
        return offset  <= headerHeight*1.5 ? offset: (int) (headerHeight * 1.5);
    }
}
