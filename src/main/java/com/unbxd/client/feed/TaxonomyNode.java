package com.unbxd.client.feed;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 07/07/14
 * Time: 3:33 PM
 *
 * Represents a taxonomy node
 */
public class TaxonomyNode {

    // Node Id. Generally corresponds to Category id or brand id
    private String nodeId;

    private String nodeName;

    // List of parents in the order of nearest first
    private List<String> parentNodeIds;

    public TaxonomyNode(String nodeId, String nodeName, List<String> parentNodeIds) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.parentNodeIds = parentNodeIds;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    /**
     * @return List of parents in the order of nearest first
     */
    public List<String> getParentNodeIds() {
        return parentNodeIds;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TaxonomyNode{");
        sb.append("nodeId='").append(nodeId).append('\'');
        sb.append(", nodeName='").append(nodeName).append('\'');
        sb.append(", parentNodeIds=").append(parentNodeIds);
        sb.append('}');
        return sb.toString();
    }
}
