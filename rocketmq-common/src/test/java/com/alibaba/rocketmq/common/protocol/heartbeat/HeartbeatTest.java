/**
 * $Id$
 */
package com.alibaba.rocketmq.common.protocol.heartbeat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.protobuf.InvalidProtocolBufferException;


/**
 * @author shijia.wxr<vintage.wang@gmail.com>
 */
public class HeartbeatTest {
    @Test
    public void test_encode_decode() throws InvalidProtocolBufferException {
        HeartbeatData heartbeatData = new HeartbeatData();
        heartbeatData.setClientID("id100");

        // producer
        for (int i = 0; i < 3; i++) {
            ProducerData data = new ProducerData();
            data.setGroupName("producer_group" + i);
            heartbeatData.getProducerDataSet().add(data);
        }

        // consumer
        for (int i = 0; i < 3; i++) {
            ConsumerData data = new ConsumerData();
            data.setGroupName("consumer_group" + i);
            data.setConsumeType(ConsumeType.CONSUME_ACTIVELY);
            data.setMessageModel(MessageModel.CLUSTERING);

            for (int k = 0; k < 3; k++) {
                SubscriptionData sub = new SubscriptionData();
                sub.setTopic("HelloTopic");
                sub.setSubString("A || B ||C");
                data.getSubscriptionDataSet().add(sub);
            }

            heartbeatData.getConsumerDataSet().add(data);
        }

        byte[] data = heartbeatData.encode();

        assertTrue(data != null);

        HeartbeatData heartbeatDataDecode = HeartbeatData.decode(data, HeartbeatData.class);

        System.out.println(heartbeatDataDecode);
    }

}
