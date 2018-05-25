package com.example.libbanner.scalebanner.holder;



public interface ScaleHolderCreator<VH extends ScaleViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
