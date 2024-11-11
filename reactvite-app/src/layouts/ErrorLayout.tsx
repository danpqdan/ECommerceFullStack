// ErrorLayout.tsx
import React from 'react';

interface ErrorLayoutProps {
  message: string;
}

export const ErrorLayout: React.FC<ErrorLayoutProps> = ({ message }) => {
  return (
    <div style={{ padding: '20px', backgroundColor: '#f8d7da', color: '#721c24', borderRadius: '5px' }}>
      <h2>Error</h2>
      <p>{message}</p>
    </div>
  );
};
