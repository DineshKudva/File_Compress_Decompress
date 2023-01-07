package com.cap_ex.decompression;

import com.cap_ex.TreeNode;

public interface IDecompress {
    TreeNode buildTree();
    String writeFile(TreeNode root, String file_path);
}
