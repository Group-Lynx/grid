declare global {
  type Todo = {
    id: string;
    title: string;
    due: string;
    detail: string;
    done: boolean;
    display: boolean;
  };

  type Mail = {
    id: string;
    title: string;
    detail: string;
    read: boolean;
  };

  type Class = {
    id: string;
    name: string;
  };
}

export {};
