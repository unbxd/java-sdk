package com.unbxd.client.feed;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sourabh
 * Date: 07/07/14
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaxonomyNode {

    private String nodeId;
    private String nodeName;
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
