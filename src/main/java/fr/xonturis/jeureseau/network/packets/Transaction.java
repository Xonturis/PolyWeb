package fr.xonturis.jeureseau.network.packets;

import fr.xonturis.jeureseau.model.impl.Callback;
import fr.xonturis.jeureseau.network.server.PacketWrapper;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Xonturis on 6/3/2020.
 */
public class Transaction<T extends Serializable> extends Packet {

    private final UUID uuid;
    @Getter
    private final String transactionName; // For client to recognize what he is talking about
    private final transient Callback<T> callback;

    public Transaction(Callback<T> callback, String transactionName) {
        super("transaction");
        this.uuid = UUID.randomUUID();
        this.transactionName = transactionName;
        this.callback = callback;
        setObject("uuid", uuid);
        new TransactionPacketHandler();
    }

    public Transaction<T> answer(T answer) {
        setObject("transaction", answer);
        return this;
    }

    public class TransactionPacketHandler extends PacketHandler {
        @PacketType(packetName = "transaction")
        public void listen(PacketWrapper wrapper) {
            if (wrapper.getPacket() instanceof Transaction && ((Transaction) wrapper.getPacket()).uuid.equals(Transaction.this.uuid)) {
                Transaction<T> transaction = (Transaction<T>) wrapper.getPacket();
                T receivedObjectFromTransaction = (T) transaction.getObject("transaction");
                callback.run(receivedObjectFromTransaction);
                this.unregister();
            }
        }
    }

}
