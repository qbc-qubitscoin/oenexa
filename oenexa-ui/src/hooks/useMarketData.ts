import { useEffect, useState, useRef } from 'react';

export interface MarketData {
  price: number;
  change: string;
}

export const useMarketData = (asset: string) => {
  const [data, setData] = useState<MarketData | null>(null);
  const [isConnected, setIsConnected] = useState(false);
  const ws = useRef<WebSocket | null>(null);

  useEffect(() => {
    const connect = () => {
      ws.current = new WebSocket('ws://localhost:8084/ws');

      ws.current.onopen = () => {
        console.log('Connected to Market Data WS');
        setIsConnected(true);
      };

      ws.current.onmessage = (event) => {
        try {
          const message = JSON.parse(event.data);
          if (message.type === 'ticker' && message.asset === asset) {
            setData(message.data);
          }
        } catch (e) {
          console.error('Failed to parse WS message', e);
        }
      };

      ws.current.onclose = () => {
        console.log('WS disconnected, reconnecting in 3s...');
        setIsConnected(false);
        setTimeout(connect, 3000);
      };

      ws.current.onerror = (err) => {
        console.error('WS Error', err);
        ws.current?.close();
      };
    };

    connect();

    return () => {
      if (ws.current) {
        ws.current.close();
      }
    };
  }, [asset]);

  return { data, isConnected };
};
