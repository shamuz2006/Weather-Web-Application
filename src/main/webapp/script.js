/**
 * 
 */
const rainContainer = document.querySelector('.rain');

for (let i = 0; i < 100; i++) {
    const drop = document.createElement('div');
    drop.classList.add('drop');
    rainContainer.appendChild(drop);
    drop.style.left = `${Math.random() * 100}%; /* Random horizontal position */`;
    drop.style.animationDuration = `${Math.random() * 1.5 + 0.5}s`; /* Random fall duration */
    drop.style.animationDelay = `${Math.random() * 2}s`; /* Random delay */
}