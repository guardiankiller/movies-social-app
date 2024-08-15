

export function AboutUs() {
    return (
        <main>
            <section className="about-section container">
                <h2>About Us</h2>
                <p>Welcome to Movie Social Site, your go-to platform for discovering, sharing, and discussing your favorite movies. Our mission is to create a vibrant community of movie enthusiasts who can connect, share reviews, and stay updated with the latest in the world of cinema.</p>
                <h3>Our Team</h3>
                <div className="team-grid">
                    <div className="team-member">
                        <img src="team1.jpg" alt="Team Member 1" />
                        <h4>John Doe</h4>
                        <p>Founder & CEO</p>
                    </div>
                    <div className="team-member">
                        <img src="team2.jpg" alt="Team Member 2" />
                        <h4>Jane Smith</h4>
                        <p>Chief Technology Officer</p>
                    </div>
                    <div className="team-member">
                        <img src="team3.jpg" alt="Team Member 3" />
                        <h4>Emily Johnson</h4>
                        <p>Marketing Director</p>
                    </div>
                </div>
            </section>
        </main>
    )
}