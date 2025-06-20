export interface User {
  id: string;
  phone: string;
  name?: string;
  lastMessage?: string;
}

export interface Message {
  id: string;
  userId: string;
  text: string;
  direction: 'IN' | 'OUT';
  timestamp: string;
}

export interface Template {
  name: string;
}
