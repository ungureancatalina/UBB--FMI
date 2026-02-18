using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenuManager : MonoBehaviour
{
    public void StartExperience()
    {
        SceneManager.LoadScene("lvl1");
    }
}