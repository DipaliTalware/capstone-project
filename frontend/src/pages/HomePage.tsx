// @ts-ignore
import hero from "../assets/hero.jpg";

const HomePage = () => {
  return (
    <div className="overflow-hidden overflow-y-hidden h-screen">
      <img
        alt="hero image"
        src={hero}
        className="w-full h-full object-cover block"
      />
    </div>
  );
};

export default HomePage;
