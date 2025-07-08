import React from 'react';

function StoreDetail({ store, products, onBack, onAddToCart, onTriggerAddedMessage }) {
    if (!store) {
        return null; // No renderizar nada si no hay tienda seleccionada
    }

    return (
        <div className="store-overlay">
            <div className="store-header">
                <button className="close-store-button" onClick={onBack}>←</button>
                <h2>{store.name}</h2>
            </div>
            <div className="product-list">
                {products.map((product, idx) => (
                    <div key={idx} className="product-card">
                        <img src={product.imageUrl} alt={product.name} />
                        <div className="product-info">
                            <h4>{product.name}</h4>
                            <p>${product.price.toFixed(2)}</p>
                        </div>
                        <button
                            className="add-to-cart-button"
                            onClick={(e) => {
                                e.stopPropagation();
                                const btn = e.currentTarget;
                                btn.classList.add('clicked');
                                setTimeout(() => btn.classList.remove('clicked'), 500);

                                onAddToCart(product, store.id);
                                onTriggerAddedMessage();
                            }}
                        >
                            +
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default StoreDetail;