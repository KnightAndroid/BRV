/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.drake.brv.sample.ui.fragment.hover

import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.listener.OnHoverAttachListener
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentHoverBinding
import com.drake.brv.sample.model.HoverHeaderModel
import com.drake.brv.sample.model.Model
import com.drake.brv.sample.model.NestedGroupModel
import com.drake.brv.utils.*
import com.drake.tooltip.toast


class HoverGridDividerFragment : BaseHoverFragment<FragmentHoverBinding>(R.layout.fragment_hover) {

    override fun initView() {
        setHasOptionsMenu(true)

        binding.rv.linear().setup {

            onCreate {
                if (it == R.layout.item_simple_list) { // 构建嵌套网格列表
                    findView<RecyclerView>(R.id.rv).divider { // 构建间距
                        setDivider(20)
                        includeVisible = true
                        orientation = DividerOrientation.GRID
                    }.grid(2).setup {
                        addType<Model>(R.layout.item_multi_type_simple_none_margin)
                    }
                }
            }
            onBind {
                if (itemViewType == R.layout.item_simple_list) { // 为嵌套的网格列表赋值数据
                    findView<RecyclerView>(R.id.rv).models =
                        getModel<NestedGroupModel>().itemSublist
                }
            }
            addType<NestedGroupModel>(R.layout.item_simple_list)
            addType<HoverHeaderModel>(R.layout.item_hover_header)

            // 点击事件
            onClick(R.id.item) {
                when (itemViewType) {
                    R.layout.item_hover_header -> toast("悬停条目")
                    else -> toast("普通条目")
                }
            }

            // 可选项, 粘性监听器
            onHoverAttachListener = object : OnHoverAttachListener {
                override fun attachHover(v: View) {
                    ViewCompat.setElevation(v, 10F) // 悬停时显示阴影
                }

                override fun detachHover(v: View) {
                    ViewCompat.setElevation(v, 0F) // 非悬停时隐藏阴影
                }
            }
        }.models = getData()
    }

    private fun getData(): List<Any> {
        return listOf(
            HoverHeaderModel(),
            NestedGroupModel(),
            HoverHeaderModel(),
            NestedGroupModel(),
            HoverHeaderModel(),
            NestedGroupModel(),
            HoverHeaderModel(),
            NestedGroupModel(),
        )
    }

    override fun initData() {
    }

}
